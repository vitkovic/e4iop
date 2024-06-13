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
    <div v-if="company" class="d-flex mb-3">
      <img
        v-if="company.logo"
        :src="companyService().retrieveImage(company.logo.filename)"
        alt="company logo"
        style="height: 100px; margin-right: 1rem;"
      />
      <h2 id="page-heading" class="jh-entity-heading" style="align-self: center; text-wrap: nowrap;">
        {{ company.name }}
      </h2>
    </div>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && threadsDTO && threadsDTO.length === 0">
      <span v-text="$t('b2BportalApp.thread.home.notFound')">No threads found</span>
    </div>
    <div class="ml-3 mb-3" style="display: flex; align-items: center;">
      <h3
        v-if="threadsDTO"
        v-text="
          threadsDTO.some(t => t.unreadExists)
            ? $t('riportalApp.thread.messages') + ' (' + threadsDTO.filter(t => t.unreadExists).length + ')'
            : $t('riportalApp.thread.messages')
        "
        class="mr-3"
      >
        Poruke
      </h3>
      <h3 v-else v-text="'Poruke'"></h3>
      <div>
        <b-button :variant="filterAllButtonVariant" v-text="$t('riportalApp.thread.filter.allnquiries')" v-on:click="showAllThreads()"
          >Svi upiti</b-button
        >
        <b-button
          :variant="filterReceiverButtonVariant"
          v-text="$t('riportalApp.thread.filter.receivedInquiries')"
          v-on:click="showReceiverThreads()"
          >Primljeni upiti</b-button
        >
        <b-button
          :variant="filterSenderButtonVariant"
          v-text="$t('riportalApp.thread.filter.sendInquiries')"
          v-on:click="showSenderThreads()"
          >Poslati upiti</b-button
        >
      </div>
    </div>
    <!-- <div class="table-responsive" v-if="threadsDTO && threadsDTO.length > 0" style="font-size: 0.9rem;"> -->
    <!-- <thead>
                <tr>
                    <th v-on:click="changeOrder('subject')"><span v-text="$t('b2BportalApp.thread.subject')">Subject</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'subject'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('companyReceiver.id')"><span v-text="'Company Receiver'">Company Receiver</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'companyReceiver.id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('companySender.id')"><span v-text="'Company Sender'">Company Sender</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'companySender.id'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead> -->
    <!-- <b-row class="table-header mt-4 bg-red ml-2 mb-2 mr-2 pt-2 font-weight-bold border-top">
        <b-col sm="5"></b-col>
        <b-col></b-col>
        <b-col><span v-text="$t('riportalApp.thread.tableHeader.receiver')">Company Receiver</span></b-col>
        <b-col><span v-text="$t('riportalApp.thread.tableHeader.sender')">Company Sender</span></b-col>
        <b-col><span v-text="$t('riportalApp.thread.tableHeader.advertisement')">Аdvertisement</span></b-col>
        <b-col></b-col>
      </b-row> -->

    <!-- <b-col v-on:click="changeOrder('subject')"><span v-text="$t('b2BportalApp.thread.subject')">Subject</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'subject'"></jhi-sort-indicator></b-col>
                    <b-col v-on:click="changeOrder('datetime')"><span v-text="'Datetime'">Datetime</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'datetime'"></jhi-sort-indicator></b-col> -->
    <!-- ovde je -->

    <!-- <b-row :class="{'gray-light': index%2 == 0, 'gray-dark': index%2 != 0 }"> -->
    <!-- <div class="accordion w-100" v-for="(thread, index) in threadsDTO" :key="thread.id">
        <b-card no-body class="mb-1 p-0">
          <b-card-header header-tag="header" class="p-0 accordion-item w-100" role="tab">
            <b-row
              :class="{ unreadMessages: thread.unreadExists }"
              class="bg-light border-top border-bottom h-100 align-items-center w-100"
              v-on:click="
                () => {
                  showMessages(thread);
                  toggleThreadCollapse('collapse-' + thread.id);
                }
              "
              v-b-toggle="'collapse-' + thread.id"
            >
              <b-col class="p-0 h-100" sm="5">
                <b-button
                  v-if="thread.collaboration && thread.collaboration.isAccepted"
                  variant="outline-primary"
                  pill
                  disabled
                  class="btn btn-sm m-1"
                >
                  <span class="d-none d-md-inline" v-text="$t('riportalApp.thread.acceptedCollaboration')">Saradnja ostvarena</span>
                </b-button>
                <b>{{ thread.subject }}</b>
                <span>{{ ' (' + thread.messageCount + ')' }}</span>
                <span>{{ buildThreadDisplayString(thread) }}</span>
              </b-col>
              <b-col class="h-100">
                <span>{{ thread.lastMessageDatetime ? $d(Date.parse(thread.lastMessageDatetime.toString()), { dateStyle: 'short' }) : '' }}</span>
              </b-col>
              <b-col class="h-100">
                <div v-if="thread.companyReceiver">
                  <router-link :to="{ name: 'CompanyView', params: { companyId: thread.companyReceiver.id } }">{{
                    thread.companyReceiver.name
                  }}</router-link>
                </div>
              </b-col>
              <b-col class="h-100">
                <div v-if="thread.companySender">
                  <router-link :to="{ name: 'CompanyView', params: { companyId: thread.companySender.id } }">{{
                    thread.companySender.name
                  }}</router-link>
                </div>
              </b-col>
              <b-col class="p-0 h-100">
                <div v-if="thread.advertisement">
                  <router-link :to="{ name: 'AdvertisementView', params: { advertisementId: thread.advertisement.id } }">{{
                    thread.advertisement.title
                  }}</router-link>
                </div>
              </b-col>
              <b-col class="text-right h-100">
                <div class="btn-group h-100">
                  <div class="btn-group  h-100">

                  <b-button
                    v-if="thread.collaboration && !thread.collaboration.isAccepted && thread.collaboration.companyOffer.id === company.id"
                    v-on:click.stop="prepareConfirmCollaboration(thread.collaboration)"
                    variant="success"
                    class="btn btn-sm m-1 my-button"
                    v-b-modal.confirmCollaboration
                  >
                    <span class="d-none d-md-inline" v-text="$t('riportalApp.thread.threadButtonGroup.confirmCollaboration')"
                      >Potvrdi saradnju</span
                    >
                  </b-button>
                  <b-button
                    v-if="thread.collaboration && !thread.collaboration.isAccepted && thread.collaboration.companyOffer.id === company.id"
                    variant="warning"
                    class="btn btn-sm m-1"
                    
                  >
                    <span class="d-none d-md-inline"
                      >Otkaži saradnju</span
                    >
                  </b-button>
                  </div>

                  <b-button v-on:click.stop="prepareRemove(thread)" variant="danger" class="btn btn-sm" v-b-modal.removeEntity>
                    <font-awesome-icon icon="times"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('riportalApp.thread.threadButtonGroup.deleteInquiry')">Obriši upit</span>
                  </b-button>
                </div>
              </b-col>
            </b-row>
          </b-card-header>

          <b-collapse :id="'collapse-' + thread.id" accordion="my-accordion" role="tabpanel">
            <b-card v-for="message in messages" :key="message.id">
              <div class="row" style="display: flex; flex-direction: row;">
                <div class="col">
                  <p>
                    <b>{{ $t('riportalApp.thread.messageSection.date') }} </b> <span></span
                    >{{ message.datetime ? $d(Date.parse(message.datetime.toString()), 'short') : '' }}
                  </p>
                  <p>
                    <b>{{ $t('riportalApp.thread.messageSection.sender') }} </b>
                    <span>{{ message.portalUserSender.company.name }}</span>
                    <span>{{ ' - ' + message.portalUserSender.user.firstName + ' ' + message.portalUserSender.user.lastName }}</span>
                  </p>
                  <p style="white-space: pre-line;">{{ message.content }}</p>
                </div>
                <div class="col col-lg-2 text-right">
                  <b-button v-on:click="deleteMessage(message, thread)" variant="danger" class="btn btn-sm m-1">
                    <font-awesome-icon icon="times"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.delete.deleteMessage')">Obriši poruku</span>
                  </b-button>
                </div>
              </div>
            </b-card>
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
          </b-collapse>
        </b-card>
      </div> -->
    <!-- </div> -->

    <!-- <p>Primalac: {{ message.portalUserReceiver.firstname + ' ' + message.portalUserReceiver.lastname }}</p> -->

    <!-- nova tabela? -->

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
        <b-col sm="4"></b-col>
        <!-- <b-col v-on:click="changeOrder('subject')"><span v-text="$t('b2BportalApp.thread.subject')">Subject</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'subject'"></jhi-sort-indicator></b-col>
                    <b-col v-on:click="changeOrder('datetime')"><span v-text="'Datetime'">Datetime</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'datetime'"></jhi-sort-indicator></b-col> -->
        <b-col><span v-text="$t('riportalApp.thread.tableHeader.receiver')">Company Receiver</span></b-col>
        <b-col><span v-text="$t('riportalApp.thread.tableHeader.sender')">Company Sender</span></b-col>
        <b-col><span v-text="$t('riportalApp.thread.tableHeader.advertisement')">Аdvertisement</span></b-col>
        <b-col sm="2"></b-col>
      </b-row>

      <!-- ovde je -->

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
                <b-col class="d-flex h-100 justify-content-between align-items-center" sm="4">
                  <div class="firstcol-p1 d-flex align-items-center">

                    <div class="d-flex align-items-center">
                      <span class="spacing-subject">
                        <b>{{ thread.subject }}</b
                        >{{ ' (' + thread.messageCount + ')' }}{{ buildThreadDisplayString(thread) }}
                      </span>
                      <!-- <b>{{ thread.subject }}</b>
                <span>{{ ' (' + thread.messageCount + ')' }}</span>
                <span>{{ buildThreadDisplayString(thread) }}</span> -->
                    </div>
                  </div>
                  <div class="firstcol-p2 d-flex align-items-center spacing">
                    <span class="text-right">
                      {{ thread.lastMessageDatetime ? $d(Date.parse(thread.lastMessageDatetime.toString()), { dateStyle: 'short' }) : '' }}
                    </span>
                  </div>
                </b-col>
                <b-col class="d-flex align-items-center h-100">
                  <div v-if="thread.companyReceiver" class="spacing-subject h-100 d-flex align-items-center">
                    <router-link :to="{ name: 'CompanyView', params: { companyId: thread.companyReceiver.id } } " class="text-body">{{
                      buildThreadColumnString(thread.companyReceiver.name)
                    }}</router-link>
                  </div>
                </b-col>
                <b-col class="h-100">

                  <div v-if="thread.isFromAdministration" class="spacing-subject h-100 d-flex align-items-center">
                    <span v-text="'ADMINISTRACIJA'" class="text-body">ADMINISTRACIJA</span>
                  </div>
                  <div v-else-if="thread.companySender" class="spacing-subject h-100 d-flex align-items-center">
                    <router-link :to="{ name: 'CompanyView', params: { companyId: thread.companySender.id } }" class="text-body">{{
                      buildThreadColumnString(thread.companySender.name)
                    }}</router-link>
                  </div>
                </b-col>
                <b-col class="h-100">
                  <div v-if="thread.advertisement" class="spacing-subject h-100 d-flex align-items-center">
                    <router-link :to="{ name: 'AdvertisementView', params: { advertisementId: thread.advertisement.id } }" class="text-body">{{
                      buildThreadColumnString(thread.advertisement.title)
                    }}</router-link>
                  </div>
                </b-col>
                <b-col class="text-right h-100 pl-0" sm="2">
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

                <div v-if="thread.isFromAdministration" class="row" style="display: flex; flex-direction: row;">
                  <div class="col">
                    <p>
                      <b>{{ $t('riportalApp.thread.messageSection.date') }} </b> 
                      <span></span>{{ message.datetime ? $d(Date.parse(message.datetime.toString()), 'short') : '' }}
                    </p>

                    <div v-if="thread.meeting">
                      <div>
                        <b>{{ 'Sastanak:' }} </b> 
                        <span></span>{{ thread.meeting.title }}
                      </div>
                      <div>
                        <b>{{ 'Organizator:' }} </b> 
                        <span></span>{{ thread.meeting.company ? thread.meeting.company.name : '' }}
                      </div>
                      <div v-if="thread.meeting.advertisement">
                        <b>{{ 'Oglas:' }} </b> 
                        <span></span>{{ thread.meeting.advertisement.title }}
                      </div>
                      <div v-if="thread.meeting.datetimeStart">
                        <b>{{ 'Vreme pocetka:' }} </b> 
                        <span></span>{{ $d(Date.parse(thread.meeting.datetimeStart.toString()), 'short') }}
                      </div>
                      <div v-if="thread.meeting.datetimeEnd">
                        <b>{{ 'Vreme zavrsetka:' }} </b> 
                        <span></span>{{ $d(Date.parse(thread.meeting.datetimeEnd.toString()), 'short') }}
                      </div>
                      <p style="white-space: pre-line;">{{ message.content }}</p>

                      <div v-if="meetingParticipant != null && !meetingParticipant.isOrganizer">
                        <div v-if="meetingParticipant.status.statusEn === meetingParticipantStatusOptions.NO_RESPONSE">
                            <button type="button" class="btn btn-secondary" v-on:click="prepareAcceptMeetingModal(thread)">
                            <span><font-awesome-icon icon="check" style="color: green;"/></span>
                            <span v-text="'Prihvati sastanak'"></span>
                          </button>
                          <button type="button" class="btn btn-secondary" v-on:click="prepareRejectMeetingModal(thread)">
                            <span><font-awesome-icon icon="times" style="color: red;"/></span>
                            <span v-text="'Otkaži sastanak'" ></span>
                          </button>
                          </div>
                          <div v-else-if="meetingParticipant.status.statusEn === meetingParticipantStatusOptions.INVITATION_ACCEPTED">
                            <span><font-awesome-icon icon="check" style="color: green;"/></span>
                            <span v-text="'Poziv za ovaj sastanak je prihvaćen'"></span>
                          </div>
                          <div v-else-if="meetingParticipant.status.statusEn === meetingParticipantStatusOptions.INVITATION_REJECTED">
                            <span><font-awesome-icon icon="times" style="color: red;"/></span>
                            <span v-text="'Poziv za ovaj sastanak je otkazan'"></span>
                          </div>
                      </div>
                      <!-- <div v-else>
                        <span>Nothing</span>
                      </div> -->
                    </div>
                    <div v-else></div>

                  </div>
                  <div class="col col-lg-2 text-right">
                      <b-button v-on:click="deleteMessage(message, thread)" variant="danger" class="btn btn-sm m-1">
                        <font-awesome-icon icon="times"></font-awesome-icon>
                        <span class="d-none d-md-inline" v-text="$t('entity.delete.deleteMessage')">Obriši poruku</span>
                      </b-button>
                    </div>
                </div>

                <div v-else class="row" style="display: flex; flex-direction: row;">
                  <div class="col">
                    <p>
                      <b>{{ $t('riportalApp.thread.messageSection.date') }} </b> <span></span
                      >{{ message.datetime ? $d(Date.parse(message.datetime.toString()), 'short') : '' }}
                    </p>
                    <p v-if="message.portalUserSender && message.portalUserSender.company">
                      <b>{{ $t('riportalApp.thread.messageSection.sender') }} </b>
                      <span>{{ message.portalUserSender.company.name }}</span>
                      <span>{{ ' - ' + message.portalUserSender.user.firstName + ' ' + message.portalUserSender.user.lastName }}</span>
                    </p>
                    <!-- <p>Primalac: {{ message.portalUserReceiver.firstname + ' ' + message.portalUserReceiver.lastname }}</p> -->
                    <p style="white-space: pre-line;">{{ message.content }}</p>
                    </div>
                    <div class="col col-lg-2 text-right">
                      <b-button v-on:click="deleteMessage(message, thread)" variant="danger" class="btn btn-sm m-1">
                        <font-awesome-icon icon="times"></font-awesome-icon>
                        <span class="d-none d-md-inline" v-text="$t('entity.delete.deleteMessage')">Obriši poruku</span>
                      </b-button>
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

    <!-- kraj -->
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

    <div v-show="threadsDTO && threadsDTO.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>

    <b-modal v-if="meeting" ref="acceptMeetingModal" id="acceptMeetingModal">
      <div class="modal-body">
          <p>
              <span v-text="'Da li želite da prihvatite poziv za sastanak - '">Da li želite da prihvatite poziv za sastanak?</span>
              <span><b>{{ meeting.title }}</b></span>
              <span v-text="'?'"></span>
          </p>
          
      </div>
      <div slot="modal-footer">
          <button type="button" class="btn btn-success" v-text="'Potvrdi'" v-on:click="acceptMeeting()">Cancel</button>
          <button
          type="button"
          class="btn btn-danger"
          v-text="'Otkaži'"
          v-on:click="closeAcceptMeetingModal()"
          >

          </button>
      </div>
  </b-modal>

  <b-modal v-if="meeting" ref="rejectMeetingModal" id="rejectMeetingModal">
      <div class="modal-body">
          <p>
              <span v-text="'Da li želite da otkažete poziv za sastanak - '">Da li želite da otkažete poziv za sastanak?</span>
              <span><b>{{ meeting.title }}</b></span>
              <span v-text="'?'"></span>
          </p>

          <label for="" v-text="'Razlog otkazivanja'"></label>
                <b-form-textarea v-model="rejectMeetingComment.comment" class="mb-3" id="" cols="30" rows="5"></b-form-textarea>

                <label for="" v-text="'Predložite novi termin'"></label>
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
          <button type="button" class="btn btn-success" v-text="'Potvrdi'" v-on:click="rejectMeeting()">Cancel</button>
          <button
          type="button"
          class="btn btn-danger"
          v-text="'Otkaži'"
          v-on:click="closeRejectMeetingModal()"
          >

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
</style>

<script lang="ts" src="./company-threads.component.ts"></script>
