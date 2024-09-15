<template>
  <div>
    <b-alert
      style="position: sticky; top: 50px;"
      :show="dismissCountDown"
      dismissible
      :variant="alertType"
      @dismissed="dismissCountDown = 0"
      @dismiss-count-down="countDownChanged"
    >
      {{ alertMessage }}
    </b-alert>
    <div v-if="company" class="d-flex mb-3 mt-5">
      <img v-if="company.logo" :src="companyService().retrieveImage(company.logo.filename)" alt="company logo" class="company-logo mr-3" />
      <h2 id="page-heading" class="jh-entity-heading company-title" style="align-self: center;">
        {{ company.name }}
      </h2>
    </div>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && threadsDTO && threadsDTO.length === 0">
      <span v-text="$t('riportalApp.thread.home.notFound')">No threads found</span>
    </div>

    <div class="container-fluid">
      <b-row class="d-flex justify-content-between mb-3">
        <h3
          v-if="threadsDTO"
          v-text="
            unreadMessagesCount ? $t('riportalApp.thread.messages') + ' (' + unreadMessagesCount + ')' : $t('riportalApp.thread.messages')
          "
          class="mr-3 mb-3 mb-sm-0"
        >
          Poruke
        </h3>
        <h3 v-else v-text="'Poruke'"></h3>
        <b-button v-on:click="prepareNewMessageModal()" variant="primary" class="btn btn-primary" size="sm" v-b-modal.newMessageModal>
          <font-awesome-icon icon="envelope"></font-awesome-icon>
          <span v-text="$t('riportalApp.thread.inquiry.newMessageAction')">Nova poruka</span>
        </b-button>
      </b-row>
      <b-row class="d-flex align-items-center">
        <b-col class="d-flex" cols="12" sm="6" lg="4">
          <div class="mb-2 mt-2 mb-sm-0 mt-sm-0">
            <b-button
              class="mb-2 mb-sm-0 mr-sm-1 btn-responsive "
              size="sm"
              :variant="filterAllButtonVariant"
              v-text="$t('riportalApp.thread.filter.allnquiries')"
              v-on:click="showAllThreads()"
              >Svi upiti</b-button
            >
            <b-button
              class="mb-2 mb-sm-0 mr-sm-1 btn-responsive "
              size="sm"
              :variant="filterReceiverButtonVariant"
              v-text="$t('riportalApp.thread.filter.receivedInquiries')"
              v-on:click="showReceiverThreads()"
              >Primljeni upiti</b-button
            >
            <b-button
              class="mb-2 mb-sm-0 btn-responsive "
              size="sm"
              :variant="filterSenderButtonVariant"
              v-text="$t('riportalApp.thread.filter.sendInquiries')"
              v-on:click="showSenderThreads()"
              >Poslati upiti</b-button
            >
          </div>
        </b-col>
        <b-col v-show="threadsDTO && threadsDTO.length > 0" cols="12" sm="6">
          <div class="row justify-content-center">
            <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
          </div>
          <div class="row justify-content-center">
            <b-pagination
              size="md"
              :total-rows="totalItems"
              v-model="page"
              :per-page="itemsPerPage"
              :change="loadPage(page)"
            ></b-pagination>
          </div>
        </b-col>
        <!-- <b-col cols="4" class="d-flex justify-content-end">
          
        </b-col> -->
      </b-row>
    </div>

    <div class="table-responsive" v-if="threadsDTO && threadsDTO.length > 0" style="font-size: 0.875rem;">
      <!-- <thead>
                <tr>
                    <th v-on:click="changeOrder('subject')"><span v-text="$t('b2BportalApp.thread.subject')">Subject</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'subject'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('companyReceiver.id')"><span v-text="'Company Receiver'">Company Receiver</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'companyReceiver.id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('companySender.id')"><span v-text="'Company Sender'">Company Sender</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'companySender.id'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead> -->
      <b-row class="table-header mt-4 mb-1 font-weight-bold border-top align-items-center spacing-subject">
        <b-col cols="10" sm="4"></b-col>
        <!-- <b-col v-on:click="changeOrder('subject')"><span v-text="$t('b2BportalApp.thread.subject')">Subject</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'subject'"></jhi-sort-indicator></b-col>
                    <b-col v-on:click="changeOrder('datetime')"><span v-text="'Datetime'">Datetime</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'datetime'"></jhi-sort-indicator></b-col> -->
        <b-col class="d-none d-md-block"><span v-text="$t('riportalApp.thread.tableHeader.receiver')">Company Receiver</span></b-col>
        <b-col class="d-none d-sm-block"><span v-text="$t('riportalApp.thread.tableHeader.sender')">Company Sender</span></b-col>
        <b-col class="d-none d-sm-block"><span v-text="$t('riportalApp.thread.tableHeader.advertisement')">Аdvertisement</span></b-col>
        <b-col cols="1"></b-col>
      </b-row>

      <b-row class="table-body ml-2 mr-2 align-items-center">
        <div class="accordion w-100" v-for="(thread, index) in threadsDTO" :key="thread.id">
          <b-card no-body class="mb-1 bg-light" :class="{ unreadMessages: thread.unreadExists }">
            <b-card-header header-tag="header" class="p-0" role="tab">
              <b-row
                class="align-items-center accordion-item p-1"
                v-on:click="
                  () => {
                    showMessages(thread);
                    toggleThreadCollapse('collapse-' + thread.id);
                  }
                "
                v-b-toggle="'collapse-' + thread.id"
              >

              <!-- OVO JE PRVA KOLONA -->
                <b-col class="d-flex h-100 justify-content-between align-items-center" cols="10" sm="4">
                  <div class="firstcol-p1 d-flex align-items-center">
                    <div class="d-flex align-items-center">
                      <span class="spacing-subject">
                        <b>{{ threadSubject(thread.subject) }}</b
                        >{{ ' (' + thread.messageCount + ')' }}{{ buildThreadDisplayString(thread) }}
                      </span>
                    </div>
                  </div>
                  <div class="firstcol-p2 d-flex align-items-center spacing">
                    <span class="text-right">
                      {{ thread.lastMessageDatetime ? $d(Date.parse(thread.lastMessageDatetime.toString()), { dateStyle: 'short' }) : '' }}
                    </span>
                  </div>
                </b-col>
                <!-- KRAJ PRVE KOLONE -->

                <!-- DRUGA KOLONA -->
                <b-col class="d-none d-md-flex align-items-center h-100">
                  <div v-if="thread.companyReceiver" class="spacing-subject h-100 d-flex align-items-center">
                    <router-link :to="{ name: 'CompanyView', params: { companyId: thread.companyReceiver.id } }" class="text-body">{{
                      buildThreadColumnString(thread.companyReceiver.name)
                    }}</router-link>
                  </div>
                </b-col>

                <!-- KRAJ DRUGE KOLONE -->

                <!-- TRECA KOLONA -->
                <b-col class="h-100 d-none d-sm-flex">
                  <div v-if="thread.isFromAdministration" class="spacing-subject h-100 d-flex align-items-center">
                    <span v-text="$t('riportalApp.thread.administration')" class="text-body">ADMINISTRACIJA</span>
                  </div>
                  <div v-else-if="thread.companySender" class="spacing-subject h-100 d-flex align-items-center">
                    <router-link :to="{ name: 'CompanyView', params: { companyId: thread.companySender.id } }" class="text-body">{{
                      buildThreadColumnString(thread.companySender.name)
                    }}</router-link>
                  </div>
                </b-col>
                <!-- KRAJ TRECE KOLONE -->
                <b-col class="h-100 d-none d-sm-flex">
                  <div v-if="thread.advertisement" class="spacing-subject h-100 d-flex align-items-center">
                    <router-link
                      :to="{ name: 'AdvertisementView', params: { advertisementId: thread.advertisement.id } }"
                      class="text-body"
                      >{{ buildThreadColumnString(thread.advertisement.title) }}</router-link
                    >
                  </div>
                </b-col>
                <b-col class="text-right h-100 pl-0" cols="1">
                  <div class="btn-group h-100">
                    <b-button
                      v-on:click.stop="prepareRemove(thread)"
                      variant="danger"
                      class="btn btn-sm spacing-subject-btn"
                      v-b-modal.removeEntity
                    >
                      <font-awesome-icon icon="trash"></font-awesome-icon>
                    </b-button>
                  </div>
                </b-col>
              </b-row>
            </b-card-header>

            <b-collapse :id="'collapse-' + thread.id" accordion="my-accordion" role="tabpanel">
              <b-card v-for="message in messages" :key="message.id">
                <div v-if="thread.isFromAdministration" class="row d-flex justify-content-between">
                  <div class="col-10">
                    <p>
                      <b>{{ $t('riportalApp.thread.messageSection.date') }} </b>
                      <span></span>{{ message.datetime ? $d(Date.parse(message.datetime.toString()), 'short') : '' }}
                    </p>

                    <div v-if="thread.meeting">
                      <div>
                        <b>{{ $t('riportalApp.thread.messageSection.meeting') }}: </b>
                        <span></span>{{ thread.meeting.title }}
                      </div>
                      <div>
                        <b>{{ $t('riportalApp.thread.messageSection.organizer') }}: </b>
                        <span></span>{{ thread.meeting.company ? thread.meeting.company.name : '' }}
                      </div>
                      <div v-if="thread.meeting.advertisement">
                        <b>{{ $t('riportalApp.thread.tableHeader.advertisement') }}: </b>
                        <span></span>{{ thread.meeting.advertisement.title }}
                      </div>
                      <div v-if="thread.meeting.datetimeStart">
                        <b>{{ $t('riportalApp.thread.messageSection.startTime') }}: </b>
                        <span></span>{{ $d(Date.parse(thread.meeting.datetimeStart.toString()), 'short') }}
                      </div>
                      <div v-if="thread.meeting.datetimeEnd">
                        <b>{{ $t('riportalApp.thread.messageSection.endTime') }}: </b>
                        <span></span>{{ $d(Date.parse(thread.meeting.datetimeEnd.toString()), 'short') }}
                      </div>
                      <p style="white-space: pre-line;">{{ messageContent(thread, message) }}</p>

                      <div v-if="meetingParticipant != null && !meetingParticipant.isOrganizer">
                        <div v-if="meetingParticipant.status.statusEn === meetingParticipantStatusOptions.NO_RESPONSE">
                          <button type="button" class="btn btn-secondary btn-sm mb-2" v-on:click="prepareAcceptMeetingModal(thread)">
                            <span><font-awesome-icon icon="check" style="color: green;" /></span>
                            <span v-text="$t('riportalApp.thread.messageSection.acceptMeeting')"></span>
                          </button>
                          <button type="button" class="btn btn-secondary btn-sm mb-2" v-on:click="prepareRejectMeetingModal(thread)">
                            <span><font-awesome-icon icon="times" style="color: red;" /></span>
                            <span v-text="$t('riportalApp.thread.messageSection.cancelMeeting')"></span>
                          </button>
                        </div>
                        <div v-else-if="meetingParticipant.status.statusEn === meetingParticipantStatusOptions.INVITATION_ACCEPTED">
                          <span><font-awesome-icon icon="check" style="color: green;" /></span>
                          <span v-text="$t('riportalApp.thread.messageSection.acceptedMeeting')"></span>
                        </div>
                        <div v-else-if="meetingParticipant.status.statusEn === meetingParticipantStatusOptions.INVITATION_REJECTED">
                          <span><font-awesome-icon icon="times" style="color: red;" /></span>
                          <span v-text="$t('riportalApp.thread.messageSection.cancelledMeeting')"></span>
                        </div>
                      </div>
                    </div>

                    <div v-if="thread.advertisementSupporter">
                      <div>
                        <b>{{ $t('riportalApp.thread.tableHeader.advertisement') }}: </b>
                        <span></span>{{ thread.advertisementSupporter.advertisement.title }}
                      </div>
                      <div>
                        <b>{{ $t('riportalApp.thread.messageSection.advertiser') }} </b>
                        <span></span>{{ thread.advertisementSupporter.advertisement.company.name }}
                      </div>

                      <p style="white-space: pre-line;">{{ message.content }}</p>

                      <div v-if="company.id === thread.advertisementSupporter.company.id">
                        <div v-if="thread.advertisementSupporter.status.nameEn === advertisementSupporterStatusOptions.NO_RESPONSE">
                          <button
                            type="button"
                            class="btn btn-secondary btn-sm mb-2 mb-2"
                            v-on:click="prepareAcceptAdvertisementSupporterModal(thread)"
                          >
                            <span><font-awesome-icon icon="check" style="color: green;" /></span>
                            <span v-text="$t('entity.action.accept')"></span>
                          </button>
                          <button
                            type="button"
                            class="btn btn-secondary btn-sm mb-2"
                            v-on:click="prepareRejectAdvertisementSupporterModal(thread)"
                          >
                            <span><font-awesome-icon icon="times" style="color: red;" /></span>
                            <span v-text="$t('entity.action.cancel')"></span>
                          </button>
                        </div>
                        <div
                          v-else-if="thread.advertisementSupporter.status.nameEn === meetingParticipantStatusOptions.INVITATION_ACCEPTED"
                        >
                          <span><font-awesome-icon icon="check" style="color: green;" /></span>
                          <span v-text="$t('riportalApp.thread.messageSection.acceptedAdvertising')"></span>
                        </div>
                        <div
                          v-else-if="thread.advertisementSupporter.status.nameEn === meetingParticipantStatusOptions.INVITATION_REJECTED"
                        >
                          <span><font-awesome-icon icon="times" style="color: red;" /></span>
                          <span v-text="$t('riportalApp.thread.messageSection.cancelledAdvertising')"></span>
                        </div>
                      </div>
                    </div>

                    <div v-else></div>
                  </div>
                  <div class="col-2 text-right">
                    <b-button v-on:click="deleteMessage(message, thread)" variant="danger" class="btn btn-sm m-1">
                      <font-awesome-icon icon="times"></font-awesome-icon>
                      <span class="d-none d-md-inline" v-text="$t('entity.delete.deleteMessage')">Obriši poruku</span>
                    </b-button>
                  </div>
                </div>

                <div v-else class="row d-flex justify-content-between">
                  <div class="col-8">
                    <p>
                      <b>{{ $t('riportalApp.thread.messageSection.date') }} </b> <span></span
                      >{{ message.datetime ? $d(Date.parse(message.datetime.toString()), 'short') : '' }}
                    </p>
                    <p v-if="message.portalUserSender && message.portalUserSender.company">
                      <b>{{ $t('riportalApp.thread.messageSection.sender') }} </b>
                      <span>{{ message.portalUserSender.company.name }}</span>
                      <span>{{ ' - ' + message.portalUserSender.user.firstName + ' ' + message.portalUserSender.user.lastName }}</span>
                    </p>
                    <p v-if="thread.advertisement" class="d-block d-sm-none">
                      <b>{{ $t('riportalApp.thread.tableHeader.advertisement') }}: </b> <span></span>{{ thread.advertisement.title }}
                    </p>
                  </div>
                  <div class="col-2">
                    <div class="text-right">
                      <b-button v-on:click="deleteMessage(message, thread)" variant="danger" class="btn btn-sm m-1">
                        <font-awesome-icon icon="times"></font-awesome-icon>
                        <span class="d-none d-md-inline" v-text="$t('entity.delete.deleteMessage')">Obriši poruku</span>
                      </b-button>
                    </div>
                  </div>
                  <!-- <p>Primalac: {{ message.portalUserReceiver.firstname + ' ' + message.portalUserReceiver.lastname }}</p> -->
                  <div class="col-8">
                    <p style="white-space: pre-line;">{{ message.content }}</p>
                  </div>
                </div>
              </b-card>

              <div v-if="!thread.isFromAdministration">
                <b-textarea
                  v-model="newMessageText"
                  class="mt-2 ml-2 mr-2 w-50"
                  :placeholder="placeholderText"
                  style="border-style: solid; border-color: darkgray;"
                >
                </b-textarea>
                <button type="button" class="btn btn-success m-2" v-text="$t('entity.action.send')" v-on:click="sendMessage(thread)">
                  Pošalji
                </button>
              </div>
            </b-collapse>
          </b-card>
        </div>
      </b-row>
    </div>

    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="b2BportalApp.thread.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span
      >
      <div class="modal-body">
        <p v-if="removeThreadDTO" id="jhi-delete-thread-heading" v-text="$t('riportalApp.thread.delete.question')">
          Are you sure you want to delete this Thread?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-thread"
          v-text="$t('entity.action.delete')"
          v-on:click="removeThread()"
        >
          Delete
        </button>
      </div>
    </b-modal>

    <b-modal v-if="meeting" ref="acceptMeetingModal" id="acceptMeetingModal">
      <div class="modal-body">
        <p>
          <span v-text="$t('riportalApp.thread.acceptMeetingModal.bodyTitle')">Da li želite da prihvatite poziv za sastanak?</span>
          <span
            ><b>{{ meeting.title }}</b></span
          >
          <span v-text="'?'"></span>
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-success" v-text="$t('entity.action.confirm')" v-on:click="acceptMeeting()">Cancel</button>
        <button type="button" class="btn btn-danger" v-text="$t('entity.action.cancel')" v-on:click="closeAcceptMeetingModal()"></button>
      </div>
    </b-modal>

    <b-modal v-if="meeting" ref="rejectMeetingModal" id="rejectMeetingModal">
      <div class="modal-body">
        <p>
          <span v-text="$t('riportalApp.thread.rejectMeetingModal.bodyTitle')">Da li želite da otkažete poziv za sastanak?</span>
          <span
            ><b>{{ meeting.title }}</b></span
          >
          <span v-text="'?'"></span>
        </p>

        <label for="" v-text="$t('riportalApp.thread.rejectMeetingModal.cancellationReason')"></label>
        <b-form-textarea v-model="rejectMeetingComment.comment" class="mb-3" id="" cols="30" rows="5"></b-form-textarea>

        <label for="" v-text="$t('riportalApp.thread.rejectMeetingModal.newAppointment')"></label>
        <div class="d-flex">
          <b-form-datepicker
            style="width: 70%;"
            v-model="rejectMeetingComment.startDate"
            :date-format-options="{ year: 'numeric', month: 'numeric', day: 'numeric' }"
            placeholder="Izaberite datum početka..."
            @input="updateBFormCalendarEndDate(rejectMeetingComment, 'startDate', 'endDate')"
          ></b-form-datepicker>
          <b-form-timepicker style="width: 30%;" minutes-step="15" v-model="rejectMeetingComment.startTime"></b-form-timepicker>
        </div>

        <div class="d-flex mb-3">
          <b-form-datepicker
            style="width: 70%;"
            v-model="rejectMeetingComment.endDate"
            :date-format-options="{ year: 'numeric', month: 'numeric', day: 'numeric' }"
            placeholder="Izaberite datum završetka..."
            :min="rejectMeetingComment.startDate"
          ></b-form-datepicker>
          <b-form-timepicker style="width: 30%;" minutes-step="15" v-model="rejectMeetingComment.endTime"></b-form-timepicker>
        </div>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-success" v-text="$t('entity.action.confirm')" v-on:click="rejectMeeting()">Cancel</button>
        <button type="button" class="btn btn-danger" v-text="$t('entity.action.cancel')" v-on:click="closeRejectMeetingModal()"></button>
      </div>
    </b-modal>

    <b-modal v-if="advertisementSupporter" ref="acceptAdvertisementSupporterModal" id="acceptAdvertisementSupporterModal">
      <div class="modal-body">
        <p>
          <span v-text="$t('riportalApp.thread.acceptAdvertisementSupporterModal.bodyTitle')"
            >Da li želite da prihvatite poziv za za zajedničko oglašavanje?</span
          >
          <br />
          <b>{{ $t('riportalApp.thread.advertisement') }}: </b>
          <span>{{ advertisementSupporter.advertisement.title }}</span>
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-success" v-text="$t('entity.action.confirm')" v-on:click="acceptAdvertisementSupporter()">
          Potvrdi
        </button>
        <button
          type="button"
          class="btn btn-danger"
          v-text="$t('entity.action.cancel')"
          v-on:click="closeAcceptAdvertisementSupporterModal()"
        ></button>
      </div>
    </b-modal>

    <b-modal v-if="advertisementSupporter" ref="rejectAdvertisementSupporterModal" id="rejectAdvertisementSupporterModal">
      <div class="modal-body">
        <p>
          <span v-text="$t('riportalApp.thread.rejectAdvertisementSupporterModal.bodyTitle')"
            >Da li želite da odbijete poziv za za zajedničko oglašavanje?</span
          >
          <br />
          <span v-text="'Oglas: '"></span>
          <span>{{ advertisementSupporter.advertisement.title }}</span>
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-success" v-text="$t('entity.action.confirm')" v-on:click="rejectAdvertisementSupporter()">
          Otkaži
        </button>
        <button
          type="button"
          class="btn btn-danger"
          v-text="$t('entity.action.cancel')"
          v-on:click="closeRejectAdvertisementSupporterModal()"
        ></button>
      </div>
    </b-modal>

    <b-modal ref="newMessageModal" id="newMessageModal">
      <span slot="modal-title">
        <span id="riportalApp.advertisement.delete.question" v-text="$t('riportalApp.thread.inquiry.newMessageTitle')"> </span>
      </span>
      <div class="modal-body">
        <label name="inquiry-subject" v-text="$t('riportalApp.thread.tableHeader.receiver')">Primalac:</label>
        <multiselect
          v-model="companyReceiver"
          :options="companies"
          :close-on-select="true"
          :clear-on-select="false"
          placeholder=""
          :selectLabel="$t('multiselect.selectLabel')"
          :selectedLabel="$t('multiselect.selectedLabel')"
          :deselectLabel="$t('multiselect.deselectLabel')"
          label="name"
          track-by="name"
          @blur="clearValidity('inputSubject')"
          class="mb-3"
        >
        </multiselect>
        <p v-if="!companyReceiverValidation.isValid" class="text-danger small" v-text="$t('entity.form.validation.title')">
          Naziv poruke ne može biti prazan.
        </p>

        <label name="inquiry-subject" v-text="$t('entity.form.messageTitle')">Naziv poruke:</label>
        <b-input v-model.trim="inputSubject.value" @blur="clearValidity('inputSubject')"></b-input>
        <p v-if="!inputSubject.isValid" class="text-danger small" v-text="$t('entity.form.validation.title')">
          Naziv poruke ne može biti prazan.
        </p>
        <br />
        <label name="inquiry-content" v-text="$t('entity.form.messageContent')">Sadržaj poruke:</label>
        <b-textarea v-model.trim="textareaContent.value" @blur="clearValidity('textareaContent')" rows="5"></b-textarea>
        <p v-if="!textareaContent.isValid" class="text-danger small" v-text="$t('entity.form.validation.content')">
          Sadržaj poruke ne može biti prazan.
        </p>
      </div>
      <div slot="modal-footer">
        <button
          type="button"
          class="btn btn-success"
          id="jhi-confirm-delete-advertisement"
          v-text="$t('entity.action.send')"
          v-on:click="sendNewMessage()"
        >
          Pošalji
        </button>
        <button type="button" class="btn btn-danger" v-text="$t('entity.action.cancel')" v-on:click="closeNewMessageModal()">
          Otkaži
        </button>
      </div>
    </b-modal>
  </div>
</template>

<style>
.unreadMessages {
  /* background-color: #eff5f1 !important; */
  box-shadow: inset 0 0 0 2px #98a7ab;
}

.accordion-item {
  height: 40px;
}

.spacing {
  letter-spacing: -0.05rem;
  font-size: 0.7rem;
}

.spacing-subject {
  letter-spacing: -0.02rem;
  line-height: 1;
  font-size: 0.8rem;
}

.spacing-subject-btn {
  letter-spacing: -0.05rem;
  line-height: 0.8;
}

.custom-tooltip {
  opacity: 1 !important;
}

.circle-indicator {
  width: 17px;
  height: 17px;
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 0; /* Remove any padding if necessary */
}

.company-logo {
  height: 75px;
}

@media screen and (max-width: 578px) {
  .company-title {
    font-size: 2rem;
  }
}

@media screen and (min-width: 768px) {
  .btn-responsive {
    font-size: 1rem;
    padding: .375rem .75rem;
  }
}

</style>

<script lang="ts" src="./company-threads.component.ts"></script>
