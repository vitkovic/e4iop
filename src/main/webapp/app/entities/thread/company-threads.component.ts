import { mixins } from 'vue-class-component';
import AlertMixin from '@/shared/alert/alert.mixin';
import Vue2Filters from 'vue2-filters';
import { Component, Vue, Inject } from 'vue-property-decorator';

import { IThread } from '@/shared/model/thread.model';
import { IMessage, Message } from '@/shared/model/message.model';
import { ICompany } from '@/shared/model/company.model';
import { IAdvertisement } from '@/shared/model/advertisement.model';
import { ICollaboration } from '@/shared/model/collaboration.model';
import { IMeeting } from '@/shared/model/meeting.model';
import { IPortalUser } from '@/shared/model/portal-user.model';
import { IMeetingParticipant } from '@/shared/model/meeting-participant.model';
import { IAdvertisementSupporter } from '@/shared/model/advertisement-supporter.model';
import { AdvertisementSupporterStatusOptions } from '@/shared/model/advertisement-supporter-status.model';
import { IInquiryDTO } from '@/shared/model/dto/inquiry-dto.model';
import { IThreadDTO } from '@/shared/model/dto/thread-dto.model';

import ThreadService from './thread.service';
import CompanyService from '@/entities/company.service';
import MessageService from '@/entities/message.service';
import CollaborationService from '@/entities/collaboration.service';
import MeetingParticipantService from '@/entities/meeting-participant/meeting-participant.service';
import PortalUserService from '../../entities/portal-user/portal-user.service';
import AdvertisementSupporterService from '@/entities/advertisement-supporter/advertisement-supporter.service';
import InquiryService from './inquiry.service';

enum ThreadsFilter {
  ALL = 'all',
  SENDER = 'sender',
  RECEIVER = 'receiver',
}

enum MeetingParticipantStatusOptions {
  INVITATION_ACCEPTED = 'Invitation accepted',
  INVITATION_REJECTED = 'Invitation rejected',
  NO_RESPONSE = 'No response',
}

interface RejectMeetingComment {
  comment: string;
  startDate: string;
  endDate: string;
  startTime: string;
  endTime: string;
}

const DEFAULT_REJECT_MEETING_COMMENT = {
  comment: '',
  startDate: '',
  endDate: '',
  startTime: '08:00:00',
  endTime: '09:00:00',
};

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Thread extends mixins(AlertMixin) {
  @Inject('threadService') private threadService: () => ThreadService;
  @Inject('companyService') private companyService: () => CompanyService;
  @Inject('messageService') private messageService: () => MessageService;
  @Inject('collaborationService') private collaborationService: () => CollaborationService;
  @Inject('meetingParticipantService') private meetingParticipantService: () => MeetingParticipantService;
  @Inject('portalUserService') private portalUserService: () => PortalUserService;
  @Inject('advertisementSupporterService') private advertisementSupporterService: () => AdvertisementSupporterService;
  @Inject('inquiryService') private inquiryService: () => InquiryService;

  private removeId: number = null;
  private removeThreadDTO: IThreadDTO | null = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;
  public newMessageText = '';
  public rejectMeetingComment = { ...DEFAULT_REJECT_MEETING_COMMENT };

  private portalUser: IPortalUser = null;
  public threads: IThread[] = [];
  public threadsDTO: IThreadDTO[] = [];
  public activeThreadDTO: IThreadDTO | null = null;
  public company: ICompany = null;
  public companyReceiver: ICompany | null = null;
  public companyId: number | null = null;
  public companies: ICompany[] = [];
  public messages: IMessage[] = [];
  public newMessage: IMessage = new Message();
  public meeting: IMeeting | null = null;
  public meetingParticipant: IMeetingParticipant | null = null;
  public advertisementSupporter: IAdvertisementSupporter | null = null;
  public countUnreadMessagesValue = 0;

  public isFetching = false;

  public meetingParticipantStatusOptions = MeetingParticipantStatusOptions;
  public advertisementSupporterStatusOptions = AdvertisementSupporterStatusOptions;
  public activeThreadFilter = ThreadsFilter.ALL;
  public filterAllButtonVariant = 'secondary';
  public filterSenderButtonVariant = 'outline-secondary';
  public filterReceiverButtonVariant = 'outline-secondary';

  public openThreadId: string | null = null;
  public isAccordionOpened = false;

  public inquiryDTO: IInquiryDTO | null = null;
  public isModalFormIsValid: boolean = true;
  public inputSubject = {
    value: '',
    isValid: true,
  };
  public textareaContent = {
    value: '',
    isValid: true,
  };
  public companyReceiverValidation = {
    value: null,
    isValid: true,
  };

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.companyId) {
        vm.companyId = to.params.companyId;

        vm.companyService()
          .find(to.params.companyId)
          .then(res => {
            vm.company = res;
            vm.getPortalUser();
            vm.retrieveThreads();
          });

        vm.getUnreadMessagesCount();
      }
    });
  }

  public clear(): void {
    this.page = 1;
    this.retrieveThreads();
  }

  public retrieveThreads(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };

    if (this.activeThreadFilter === ThreadsFilter.ALL) {
      this.threadService()
        .retrieveByCompany(this.company.id, paginationQuery)
        .then(
          res => {
            this.threadsDTO = res.data;
            this.totalItems = Number(res.headers['x-total-count']);
            this.queryCount = this.totalItems;
            this.isFetching = false;
          },
          err => {
            this.isFetching = false;
          }
        );
    } else if (this.activeThreadFilter === ThreadsFilter.SENDER) {
      this.threadService()
        .retrieveByCompanySender(this.company.id, paginationQuery)
        .then(
          res => {
            this.threadsDTO = res.data;
            this.totalItems = Number(res.headers['x-total-count']);
            this.queryCount = this.totalItems;
            this.isFetching = false;
          },
          err => {
            this.isFetching = false;
          }
        );
    } else if (this.activeThreadFilter === ThreadsFilter.RECEIVER) {
      this.threadService()
        .retrieveByCompanyReceiver(this.company.id, paginationQuery)
        .then(
          res => {
            this.threadsDTO = res.data;
            this.totalItems = Number(res.headers['x-total-count']);
            this.queryCount = this.totalItems;
            this.isFetching = false;
          },
          err => {
            this.isFetching = false;
          }
        );
    }
  }

  public getPortalUser() {
    const user = this.$store.getters.account;
    if (user) {
      this.portalUserService()
        .findByUserId(user.id)
        .then(res => {
          this.portalUser = res;
          if (this.portalUser.company?.id === this.company.id) {
          }
        });
    }
  }

  public prepareRemove(thread: IThreadDTO): void {
    this.removeThreadDTO = thread;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeThread(): void {
    if (!this.removeThreadDTO?.id) {
      return;
    }

    let isDeletedSender = true;

    if (this.removeThreadDTO.isFromAdministration) {
      isDeletedSender = false;
    } else {
      const companySenderId = this.removeThreadDTO.companySender.id;
      const companyReceiverId = this.removeThreadDTO.companyReceiver.id;
      const portalUserCompanyId = this.portalUser.company.id;

      // Odredjivanje za koga treba da se sakrije poruka.
      if (portalUserCompanyId === companySenderId) {
        isDeletedSender = true;
      } else if (portalUserCompanyId === companyReceiverId) {
        isDeletedSender = false;
      }
    }

    this.threadService()
      .hideUserMessages(this.removeThreadDTO.id, isDeletedSender)
      .then(() => {
        const notificatonMessage = this.$t('riportalApp.thread.deletedConversation');
        this.$notify({
          text: notificatonMessage,
        });
        this.threadService()
          .deleteIfEmpty(this.removeThreadDTO.id)
          .then(res => {
            this.retrieveThreads();
            // Ukoliko thread nije obrisan, osvezi poruke u threadu
          });
      });

    this.closeDialog();
  }

  public sort(): Array<any> {
    const result = [this.propOrder + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.propOrder !== 'id') {
      result.push('id');
    }
    return result;
  }

  public loadPage(page: number): void {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  public transition(): void {
    this.retrieveThreads();
  }

  public changeOrder(propOrder): void {
    this.propOrder = propOrder;
    this.reverse = !this.reverse;
    this.transition();
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }

  public showAllThreads(): void {
    this.activeThreadFilter = ThreadsFilter.ALL;
    this.collapseAllThreads(this.openThreadId);
    this.retrieveThreads();
    this.getUnreadMessagesCount();

    this.filterAllButtonVariant = 'secondary';
    this.filterSenderButtonVariant = 'outline-secondary';
    this.filterReceiverButtonVariant = 'outline-secondary';
  }

  public showSenderThreads(): void {
    this.activeThreadFilter = ThreadsFilter.SENDER;
    this.collapseAllThreads(this.openThreadId);
    this.retrieveThreads();
    this.getUnreadMessagesCount();

    this.filterAllButtonVariant = 'outline-secondary';
    this.filterSenderButtonVariant = 'secondary';
    this.filterReceiverButtonVariant = 'outline-secondary';
  }

  public showReceiverThreads(): void {
    this.activeThreadFilter = ThreadsFilter.RECEIVER;
    this.collapseAllThreads(this.openThreadId);
    this.retrieveThreads();
    this.getUnreadMessagesCount();

    this.filterAllButtonVariant = 'outline-secondary';
    this.filterSenderButtonVariant = 'outline-secondary';
    this.filterReceiverButtonVariant = 'secondary';
  }

  public getMessages(thread: IThreadDTO) {
    if (thread?.id) {
      this.messageService()
        .getAllByThreadAndCompany(thread.id, this.companyId)
        .then(res => {
          this.messages = res;
        });
    }
  }

  public showMessages(thread: IThreadDTO | null) {
    if (thread == null) {
      return;
    }

    // this.meetingParticipant = null;
    if (thread?.meeting) {
      this.meetingParticipantService()
        .findOneForMeetingAndCompany(thread.meeting.id, this.companyId)
        .then(res => {
          this.meetingParticipant = res;
        });
    }

    if (thread?.id) {
      this.messageService()
        .getAllByThreadAndCompany(thread.id, this.companyId)
        .then(res => {
          this.retrieveThreads();
          this.getUnreadMessagesCount();
          this.messages = res;
        });
    }
  }

  public buildThreadDisplayString(thread: IThreadDTO): String {
    const CHAR_LIMIT = 60;
    const subjectLength = thread.subject.length;
    let displayString = '';

    if (!thread?.lastMessageContent) {
      return displayString;
    }

    if (thread.lastMessageContent.length + subjectLength <= CHAR_LIMIT) {
      return ' - ' + thread.lastMessageContent;
    }

    displayString = ' - ' + thread.lastMessageContent.substring(0, CHAR_LIMIT - subjectLength) + '...';

    return displayString;
  }

  public sendMessage(thread: IThreadDTO) {
    const formData = new FormData();
    formData.append('content', '' + this.newMessageText);
    formData.append('companyId', '' + this.companyId);
    formData.append('portalUserId', '' + this.portalUser.id);
    formData.append('threadId', '' + thread.id);

    this.messageService()
      .createMessageInThread(formData)
      .then(res => {
        this.retrieveThreads();
        this.getMessages(thread);
      });

    this.newMessageText = '';
  }

  public deleteMessage(message: IMessage, thread: IThreadDTO) {
    if (!message?.id) {
      return;
    }

    // Prvo se povlaci aktuelno stanje poruke u bazi
    // za slucaj da je u browser-u ucitana stara verzija.
    // Ovo resava problem ukoliko jedan korisnik obrise poruku,
    // a drugi korisnik nije osvezio poruku pre brisanja na svom kraju.
    this.messageService()
      .find(message.id)
      .then(res => {
        // Aktuelno stanje poruke u bazi
        const messageFromDB = res;

        // Odredjivanje za koga treba da se sakrije poruka.
        if (thread?.companySender?.id && this.company.id === thread.companySender.id) {
          messageFromDB.isDeletedSender = true;
        } else if (thread?.companyReceiver?.id && this.company.id === thread.companyReceiver.id) {
          messageFromDB.isDeletedReceiver = true;
        }

        if (messageFromDB.isDeletedSender && messageFromDB.isDeletedReceiver) {
          this.messageService()
            .delete(messageFromDB.id)
            .then(res => {
              const notificatonMessage = this.$t('riportalApp.thread.messageSection.deleteMessage');
              this.$notify({
                text: notificatonMessage,
              });
              this.threadService()
                .deleteIfEmpty(thread.id)
                .then(res => {
                  this.retrieveThreads();
                  // Ukoliko thread nije obrisan, osvezi poruke u threadu
                  if (res.data === false) {
                    this.getMessages(thread);
                  }
                });
            });
        } else {
          this.messageService()
            .update(messageFromDB)
            .then(res => {
              const notificatonMessage = this.$t('riportalApp.thread.messageSection.deleteMessage');
              this.$notify({
                text: notificatonMessage,
              });
              this.retrieveThreads();
              this.getMessages(thread);
            });
        }
      });
  }

  public toggleThreadCollapse(threadId) {
    if (this.openThreadId === threadId) {
      this.isAccordionOpened = !this.isAccordionOpened;
    } else {
      this.openThreadId = threadId;
      this.isAccordionOpened = true;
    }
  }

  private collapseAllThreads(string): void {
    if (this.isAccordionOpened) {
      const threadId = string;
      this.$root.$emit('bv::toggle::collapse', threadId);
      this.isAccordionOpened = false;
      this.openThreadId = null;
    }
  }

  get placeholderText(): string {
    return this.$t('riportalApp.thread.messageSection.placeholder') as string;
  }

  public buildThreadColumnString(name: string): String {
    const CHAR_LIMIT = 40;
    let displayString = '';

    if (name.length <= CHAR_LIMIT) {
      return name;
    } else {
      return (displayString = name.slice(0, CHAR_LIMIT - 3) + '...');
    }
  }

  public prepareAcceptMeetingModal(threadDTO: IThreadDTO): void {
    this.activeThreadDTO = threadDTO;
    this.meeting = threadDTO.meeting;

    if (<any>this.$refs.acceptMeetingModal) {
      (<any>this.$refs.acceptMeetingModal).show();
    }
  }

  public acceptMeeting(): void {
    this.meetingParticipantService()
      .acceptMeetingForCompany(this.meeting.id, this.companyId)
      .then(res => {
        this.showMessages(this.activeThreadDTO);
        const message = 'Prihvatili ste poziv za sastanak - ' + this.meeting.title;
        this.$notify({
          text: message,
        });
      })
      .catch(error => {
        if (error.response) {
          const status = error.response.status;
          // const message = error.response.data;

          if (status === 404) {
            const message = 'Sistemska greska, nemate poziv za izabrani sastanak';
            this.$notify({
              text: message,
            });
          } else if (status === 400) {
            const message = 'Već ste prihvatili poziv za ovaj sastanak';
            this.$notify({
              text: message,
            });
          } else {
            const message = error.response.data;
            this.$notify({
              text: message,
            });
          }
        } else {
          console.error('Greska!', error.message);
        }
      });

    this.closeAcceptMeetingModal();
  }

  public closeAcceptMeetingModal(): void {
    (<any>this.$refs.acceptMeetingModal).hide();
  }

  public acceptMeeting(): void {
    this.meetingParticipantService()
      .acceptMeetingForCompany(this.meeting.id, this.companyId)
      .then(res => {
        this.showMessages(this.activeThreadDTO);
        const message = 'Prihvatili ste poziv za sastanak - ' + this.meeting.title;
        this.$notify({
          text: message,
        });
      })
      .catch(error => {
        if (error.response) {
          const status = error.response.status;
          // const message = error.response.data;

          if (status === 404) {
            const message = 'Sistemska greska, nemate poziv za izabrani sastanak';
            this.$notify({
              text: message,
            });
          } else if (status === 400) {
            const message = 'Već ste prihvatili poziv za ovaj sastanak';
            this.$notify({
              text: message,
            });
          } else {
            const message = error.response.data;
            this.$notify({
              text: message,
            });
          }
        } else {
          console.error('Greska!', error.message);
        }
      });

    this.closeAcceptMeetingModal();
  }

  public prepareRejectMeetingModal(threadDTO: IThreadDTO): void {
    this.activeThreadDTO = threadDTO;
    this.meeting = threadDTO.meeting;
    this.rejectMeetingComment = { ...DEFAULT_REJECT_MEETING_COMMENT };
    this.rejectMeetingComment.startDate = this.formatDateStringFromDate(new Date());
    this.rejectMeetingComment.endDate = this.formatDateStringFromDate(new Date());

    if (<any>this.$refs.rejectMeetingModal) {
      (<any>this.$refs.rejectMeetingModal).show();
    }
  }

  public acceptMeeting(): void {
    this.meetingParticipantService()
      .acceptMeetingForCompany(this.meeting.id, this.companyId)
      .then(res => {
        this.showMessages(this.activeThreadDTO);
        const message = 'Prihvatili ste poziv za sastanak - ' + this.meeting.title;
        this.$notify({
          text: message,
        });
      })
      .catch(error => {
        if (error.response) {
          const status = error.response.status;
          // const message = error.response.data;

          if (status === 404) {
            const message = 'Sistemska greska, nemate poziv za izabrani sastanak';
            this.$notify({
              text: message,
            });
          } else if (status === 400) {
            const message = 'Već ste prihvatili poziv za ovaj sastanak';
            this.$notify({
              text: message,
            });
          } else {
            const message = error.response.data;
            this.$notify({
              text: message,
            });
          }
        } else {
          console.error('Greska!', error.message);
        }
      });

    this.closeAcceptMeetingModal();
  }

  public rejectMeeting(): void {
    const rejectMeetingString = this.formatRejectMeetingComment();

    let formData = new FormData();
    formData.append('meetingId', '' + this.meeting.id);
    formData.append('companyId', '' + this.companyId);
    formData.append('comment', '' + rejectMeetingString);

    this.meetingParticipantService()
      .rejectMeetingForCompany(formData)
      .then(res => {
        this.showMessages(this.activeThreadDTO);
        const message = this.$t('riportalApp.thread.meetingCancellation');
        +this.meeting.title;
        this.$notify({
          text: message,
        });
      })
      .catch(error => {
        if (error.response) {
          const status = error.response.status;
          // const message = error.response.data;

          if (status === 404) {
            const message = this.$t('riportalApp.thread.systemError');
            this.$notify({
              text: message,
            });
          } else if (status === 400) {
            const message = this.$t('riportalApp.thread.meetingCancellationError');
            this.$notify({
              text: message,
            });
          } else {
            const message = error.response.data;
            this.$notify({
              text: message,
            });
          }
        } else {
          console.error('Greska!', error.message);
        }
      });

    this.closeRejectMeetingModal();
  }

  public closeRejectMeetingModal(): void {
    (<any>this.$refs.rejectMeetingModal).hide();
  }

  public prepareAcceptAdvertisementSupporterModal(threadDTO: IThreadDTO): void {
    this.activeThreadDTO = threadDTO;
    this.advertisementSupporter = threadDTO.advertisementSupporter;

    if (<any>this.$refs.acceptAdvertisementSupporterModal) {
      (<any>this.$refs.acceptAdvertisementSupporterModal).show();
    }
  }

  public acceptAdvertisementSupporter(): void {
    let message = '';
    this.advertisementSupporterService()
      .acceptForCompany(this.advertisementSupporter.advertisement.id, this.advertisementSupporter.company.id)
      .then(res => {
        // this.showMessages(this.activeThreadDTO);
        this.retrieveThreads();
        message = this.$t('riportalApp.thread.advertisementSupporter.acceptAdvertisementSupporter');
        this.$notify({
          text: message,
        });
      })
      .catch(error => {
        if (error.response) {
          const status = error.response.status;
          // const message = error.response.data;

          if (status === 404) {
            message = this.$t('riportalApp.thread.advertisementSupporter.systemError');
            this.$notify({
              text: message,
            });
          } else if (status === 400) {
            message = this.$t('riportalApp.thread.advertisementSupporter.advertisingCancellationError');
            this.$notify({
              text: message,
            });
          } else {
            message = error.response.data;
            this.$notify({
              text: message,
            });
          }
        } else {
          console.error('Greska!', error.message);
        }
      });

    this.closeAcceptAdvertisementSupporterModal();
  }

  public closeAcceptAdvertisementSupporterModal(): void {
    (<any>this.$refs.acceptAdvertisementSupporterModal).hide();
  }

  public prepareRejectAdvertisementSupporterModal(threadDTO: IThreadDTO): void {
    this.activeThreadDTO = threadDTO;
    this.advertisementSupporter = threadDTO.advertisementSupporter;

    if (<any>this.$refs.rejectAdvertisementSupporterModal) {
      (<any>this.$refs.rejectAdvertisementSupporterModal).show();
    }
  }

  public rejectAdvertisementSupporter(): void {
    let message = '';
    this.advertisementSupporterService()
      .rejectForCompany(this.advertisementSupporter.advertisement.id, this.advertisementSupporter.company.id)
      .then(res => {
        // this.showMessages(this.activeThreadDTO);
        this.retrieveThreads();
        message = this.$t('riportalApp.thread.advertisementSupporter.rejectAdvertisementSupporter');
        this.$notify({
          text: message,
        });
      })
      .catch(error => {
        if (error.response) {
          const status = error.response.status;
          // const message = error.response.data;

          if (status === 404) {
            message = this.$t('riportalApp.thread.advertisementSupporter.systemError');
            this.$notify({
              text: message,
            });
          } else if (status === 400) {
            message = this.$t('riportalApp.thread.advertisementSupporter.advertisingCancellationError');
            this.$notify({
              text: message,
            });
          } else {
            message = error.response.data;
            this.$notify({
              text: message,
            });
          }
        } else {
          console.error('Greska!', error.message);
        }
      });

    this.closeRejectAdvertisementSupporterModal();
  }

  public closeRejectAdvertisementSupporterModal(): void {
    (<any>this.$refs.rejectAdvertisementSupporterModal).hide();
  }

  public formatRejectMeetingComment(): string {
    const { comment, endDate, endTime, startDate, startTime } = this.rejectMeetingComment;
    const formattedStartDate = this.formatDateStringToDateString(startDate);
    const formattedEndDate = this.formatDateStringToDateString(endDate);
    let proposedTime: string;

    if (startDate === endDate) {
      proposedTime = `${formattedStartDate} ${startTime.substring(0, 5)} - ${endTime.substring(0, 5)}`;
    } else {
      proposedTime = `${formattedStartDate} ${startTime.substring(0, 5)}\n${formattedEndDate} ${endTime.substring(0, 5)}`;
    }

    return (
      `____________________\n\n` +
      `RAZLOG OTKAZIVANJA\n` +
      `${comment}\n\n` +
      `NOVO PREDLOŽENO VREME\n` +
      `${proposedTime}\n\n` +
      `____________________`
    );
  }

  /**
   * Format Date object to date string 'yyyy-MM-dd'.
   * Needed for initializing v-model of b-form-datepicker.
   */
  public formatDateStringFromDate(date: Date): string {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');

    return `${year}-${month}-${day}`;
  }

  /**
   * Format date string to date string 'MM.dd.YYYY'.
   */
  public formatDateStringToDateString(dateString: string): string {
    const date = new Date(dateString);
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const day = date.getDate().toString().padStart(2, '0');
    const year = date.getFullYear();
    return `${day}.${month}.${year}`;
  }

  public updateBFormCalendarEndDate<T>(obj: T, startDateKey: keyof T, endDateKey: keyof T) {
    if (obj[endDateKey] < obj[startDateKey]) {
      obj[endDateKey] = obj[startDateKey];
    }
  }

  public prepareNewMessageModal(): void {
    this.companyService()
      .getAllAutocompleteByNameWithoutExlcuded(' ', [this.companyId])
      .then(res => {
        this.companies = res.data;
      });

    if (<any>this.$refs.newMessageModal) {
      (<any>this.$refs.newMessageModal).show();
    }
  }

  public sendNewMessage(): void {
    this.validateModalForm();

    if (!this.isModalFormIsValid) {
      return;
    }

    this.inquiryDTO = {
      advertisement: null,
      datetime: new Date(),
      subject: this.inputSubject.value,
      content: this.textareaContent.value,
      companySender: this.company,
      companyReceiver: this.companyReceiver,
      portalUserSender: this.portalUser,
    };

    if (this.inquiryDTO) {
      this.inquiryService()
        .create(this.inquiryDTO)
        .then(res => {
          const message = 'Vaša poruka je poslata kompaniji ' + this.companyReceiver.name;
          this.$notify({
            text: message,
          });
          this.retrieveThreads();
        });
    }

    this.closeNewMessageModal();
  }

  public closeNewMessageModal(): void {
    (<any>this.$refs.newMessageModal).hide();
  }

  public validateModalForm() {
    this.isModalFormIsValid = true;
    if (this.companyReceiver === null) {
      this.companyReceiverValidation.isValid = false;
      this.isModalFormIsValid = false;
    }

    if (this.inputSubject.value === '') {
      this.inputSubject.isValid = false;
      this.isModalFormIsValid = false;
    }

    if (this.textareaContent.value === '') {
      this.textareaContent.isValid = false;
      this.isModalFormIsValid = false;
    }
  }

  public clearValidity(input) {
    this[input].isValid = true;
  }

  public async getUnreadMessagesCount() {
    try {
      this.countUnreadMessagesValue = await this.messageService().getCountUnreadForCompany(this.companyId);
    } catch (err) {
      console.error(err);
    }
  }

  get unreadMessagesCount(): number {
    return this.countUnreadMessagesValue;
  }
}
