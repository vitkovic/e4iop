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

import ThreadService from './thread.service';
import CompanyService from '@/entities/company.service';
import MessageService from '@/entities/message.service';
import CollaborationService from '@/entities/collaboration.service';
import MeetingParticipantService from '@/entities/meeting-participant/meeting-participant.service';
import PortalUserService from '../../entities/portal-user/portal-user.service';

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

interface IThreadDTO {
  id: number;
  subject: string;
  isFromAdministration: boolean;
  companySender: ICompany;
  companyReceiver: ICompany;
  advertisement: IAdvertisement;
  collaboration: ICollaboration;
  meeting: IMeeting;
  messageCount: number;
  lastMessageDatetime: Date;
  lastMessageContent: string;
  unreadExists: boolean;
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

  private removeId: number = null;
  private removeThreadDTO: IThreadDTO = null;
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
  public companyId: number | null = null;
  public messages: IMessage[] = [];
  public newMessage: IMessage = new Message();
  public meeting: IMeeting | null = null;
  public meetingParticipant: IMeetingParticipant | null = null;

  public isFetching = false;

  public meetingParticipantStatusOptions = MeetingParticipantStatusOptions;
  public activeThreadFilter = ThreadsFilter.ALL;
  public filterAllButtonVariant = 'secondary';
  public filterSenderButtonVariant = 'outline-secondary';
  public filterReceiverButtonVariant = 'outline-secondary';

  public openThreadId: string | null = null;

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
      }
    });
  }

  // public mounted(): void {
  //   this.retrieveAllThreads();
  // }

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
            console.log(this.threadsDTO);
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

    const companySenderId = this.removeThreadDTO.companySender.id;
    const companyReceiverId = this.removeThreadDTO.companyReceiver.id;
    const portalUserCompanyId = this.portalUser.company.id;

    // Odredjivanje za koga treba da se sakrije poruka.
    let isDeletedSender = true;
    if (portalUserCompanyId === companySenderId) {
      isDeletedSender = true;
    } else if (portalUserCompanyId === companyReceiverId) {
      isDeletedSender = false;
    }

    this.threadService()
      .hideUserMessages(this.removeThreadDTO.id, isDeletedSender)
      .then(() => {
        const notificatonMessage = 'Konverzacija je obrisana!';
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
    this.retrieveThreads();
    this.collapseAllThreads(this.openThreadId);

    this.filterAllButtonVariant = 'secondary';
    this.filterSenderButtonVariant = 'outline-secondary';
    this.filterReceiverButtonVariant = 'outline-secondary';
  }

  public showSenderThreads(): void {
    this.activeThreadFilter = ThreadsFilter.SENDER;
    this.retrieveThreads();
    this.collapseAllThreads(this.openThreadId);

    this.filterAllButtonVariant = 'outline-secondary';
    this.filterSenderButtonVariant = 'secondary';
    this.filterReceiverButtonVariant = 'outline-secondary';
  }

  public showReceiverThreads(): void {
    this.activeThreadFilter = ThreadsFilter.RECEIVER;
    this.retrieveThreads();
    this.collapseAllThreads(this.openThreadId);

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
          // this.retrieveThreads();
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
    formData.append('senderId', '' + this.portalUser.id);
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
    if (!(message?.id && this.portalUser && thread?.companySender?.id && thread?.companyReceiver?.id)) {
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

        const companySenderId = thread.companySender.id;
        const companyReceiverId = thread.companyReceiver.id;
        const portalUserCompanyId = this.portalUser.company.id;

        // Odredjivanje za koga treba da se sakrije poruka.
        if (portalUserCompanyId === companySenderId) {
          messageFromDB.isDeletedSender = true;
        } else if (portalUserCompanyId === companyReceiverId) {
          messageFromDB.isDeletedReceiver = true;
        }

        if (messageFromDB.isDeletedSender === messageFromDB.isDeletedReceiver) {
          this.messageService()
            .delete(messageFromDB.id)
            .then(res => {
              const notificatonMessage = 'Poruka je obrisana!';
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
              const notificatonMessage = 'Poruka je obrisana!';
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
    this.openThreadId = threadId;
  }

  private collapseAllThreads(string): void {
    const threadId = string;
    this.$root.$emit('bv::toggle::collapse', threadId);
    this.openThreadId = null;
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
        const message = 'Otkazali ste poziv za sastanak - ' + this.meeting.title;
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
            const message = 'Već ste odbili poziv za ovaj sastanak';
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
}
