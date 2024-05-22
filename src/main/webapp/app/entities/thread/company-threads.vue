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
        v-text="threadsDTO.some(t => t.unreadExists) ? $t('riportalApp.thread.messages') + ' (' + threadsDTO.filter(t => t.unreadExists).length + ')' : $t('riportalApp.thread.messages')"
        class="mr-3"
      >
        Poruke
      </h3>
      <h3 v-else v-text="'Poruke'"></h3>
      <div>
        <b-button :variant="filterAllButtonVariant" v-text="$t('riportalApp.thread.filter.allnquiries')" v-on:click="showAllThreads()">Svi upiti</b-button>
        <b-button :variant="filterReceiverButtonVariant" v-text="$t('riportalApp.thread.filter.receivedInquiries')" v-on:click="showReceiverThreads()">Primljeni upiti</b-button>
        <b-button :variant="filterSenderButtonVariant" v-text="$t('riportalApp.thread.filter.sendInquiries')" v-on:click="showSenderThreads()">Poslati upiti</b-button>
      </div>
    </div>
    <div class="table-responsive" v-if="threadsDTO && threadsDTO.length > 0" style="font-size: 0.9rem;">
      <!-- <thead>
                <tr>
                    <th v-on:click="changeOrder('subject')"><span v-text="$t('b2BportalApp.thread.subject')">Subject</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'subject'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('companyReceiver.id')"><span v-text="'Company Receiver'">Company Receiver</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'companyReceiver.id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('companySender.id')"><span v-text="'Company Sender'">Company Sender</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'companySender.id'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead> -->
      <b-row class="table-header mt-4 bg-red ml-2 mb-2 mr-2 pt-2 font-weight-bold border-top">
        <b-col sm="5"></b-col>
        <b-col></b-col>
        <!-- <b-col v-on:click="changeOrder('subject')"><span v-text="$t('b2BportalApp.thread.subject')">Subject</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'subject'"></jhi-sort-indicator></b-col>
                    <b-col v-on:click="changeOrder('datetime')"><span v-text="'Datetime'">Datetime</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'datetime'"></jhi-sort-indicator></b-col> -->
        <b-col><span v-text="$t('riportalApp.thread.tableHeader.receiver')">Company Receiver</span></b-col>
        <b-col><span v-text="$t('riportalApp.thread.tableHeader.sender')">Company Sender</span></b-col>
        <b-col><span v-text="$t('riportalApp.thread.tableHeader.advertisement')">Аdvertisement</span></b-col>
        <b-col></b-col>
      </b-row>
      <div class="accordion" v-for="(thread, index) in threadsDTO" :key="thread.id">
        <!-- <b-row :class="{'gray-light': index%2 == 0, 'gray-dark': index%2 != 0 }"> -->
        <b-row
          :class="{ unreadMessages: thread.unreadExists }"
          class="bg-light ml-2 mr-2 border-top border-bottom"
          v-on:click="() => { showMessages(thread); toggleThreadCollapse('collapse-' + thread.id); }"
          v-b-toggle="'collapse-' + thread.id"
        >
          <b-col class="pt-2" sm="5">
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
          <b-col class="pt-2">
            <span>{{ thread.lastMessageDatetime ? $d(Date.parse(thread.lastMessageDatetime.toString()), 'short') : '' }}</span>
          </b-col>
          <b-col class="pt-2">
            <div v-if="thread.companyReceiver">
              <router-link :to="{ name: 'CompanyView', params: { companyId: thread.companyReceiver.id } }">{{
                thread.companyReceiver.name
              }}</router-link>
            </div>
          </b-col>
          <b-col class="pt-2">
            <div v-if="thread.companySender">
              <router-link :to="{ name: 'CompanyView', params: { companyId: thread.companySender.id } }">{{
                thread.companySender.name
              }}</router-link>
            </div>
          </b-col>
          <b-col class="pt-2">
            <div v-if="thread.advertisement">
              <router-link :to="{ name: 'AdvertisementView', params: { advertisementId: thread.advertisement.id } }">{{
                thread.advertisement.title
              }}</router-link>
            </div>
          </b-col>
          <b-col class="text-right">
            <div class="btn-group">
              <b-button
                v-if="thread.collaboration && !thread.collaboration.isAccepted && thread.collaboration.companyOffer.id === company.id"
                v-on:click.stop="prepareConfirmCollaboration(thread.collaboration)"
                variant="success"
                class="btn btn-sm m-1"
                v-b-modal.confirmCollaboration
              >
                <span class="d-none d-md-inline" v-text="$t('riportalApp.thread.threadButtonGroup.confirmCollaboration')">Potvrdi saradnju</span>
              </b-button>

              <b-button v-on:click.stop="prepareRemove(thread)" variant="danger" class="btn btn-sm m-1" v-b-modal.removeEntity>
                <font-awesome-icon icon="times"></font-awesome-icon>
                <span class="d-none d-md-inline" v-text="$t('riportalApp.thread.threadButtonGroup.deleteInquiry')">Obriši upit</span>
              </b-button>
            </div>
          </b-col>
        </b-row>
        <b-collapse :id="'collapse-' + thread.id" accordion="my-accordion">
          <b-card v-for="message in messages" :key="message.id">
            <div class="row" style="display: flex; flex-direction: row;">
              <div class="col">
                <p><b>{{$t('riportalApp.thread.messageSection.date')}} </b> <span></span>{{ message.datetime ? $d(Date.parse(message.datetime.toString()), 'short') : '' }}</p>
                <p>
                  <b>{{$t('riportalApp.thread.messageSection.sender')}} </b>
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
          <b-textarea
            v-model="newMessageText"
            class="mt-2 ml-2 mr-2 w-50"
            :placeholder="placeholderText"
            style="border-style: solid; border-color: darkgray;"
          >
          </b-textarea>
          <button type="button" class="btn btn-success m-2" v-text="$t('entity.action.send')" v-on:click="sendMessage(thread)">Pošalji</button>
        </b-collapse>
      </div>
    </div>
    <b-modal v-if="collaboration" ref="confirmCollaboration" id="confirmCollaboration">
      <span slot="modal-title"
        ><span id="riportalApp.advertisement.delete.question" v-text="$t('riportalApp.thread.modal.title')">Da li želite da potvrdite zahtev za saradnju?</span></span
      >
      <div class="modal-body">
        <p id="jhi-delete-advertisement-heading"><b>{{$t('riportalApp.thread.modal.advertisement')}} </b>{{ collaboration.advertisement.title }}</p>
        <p id="jhi-delete-advertisement-heading"><b>{{$t('riportalApp.thread.modal.company')}} </b>{{ collaboration.companyRequest.name }}</p>
        <br />
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-danger" v-text="$t('entity.action.cancel')" v-on:click="closeConfirmCollaboration()">Otkaži</button>
        <button type="button" class="btn btn-success" id="jhi-confirm-delete-advertisement" v-text="$t('entity.action.confirm')" v-on:click="confirmCollaboration()" v-b-modal.confirmCollaborationSecond>
          Potvrdi
        </button>
      </div>
    </b-modal>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="b2BportalApp.thread.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span
      >
      <div class="modal-body">
        <p
          v-if="removeThreadDTO"
          id="jhi-delete-thread-heading"
          v-text="$t('riportalApp.thread.delete.question')"
        >
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
    <b-modal v-if="collaboration" ref="confirmCollaborationSecond" id="confirmCollaborationSecond">
      <!-- <span slot="modal-title"
        ><span id="riportalApp.advertisement.delete.question">Da li želite da potvrdite zahtev za saradnju?</span></span
      > -->
      <div class="modal-body">
        <p>Postoji više zahteva za saradnju na oglasu {{ collaboration.advertisement.title }}, želite li da odbijete ostale zahteve?</p>
        <br />
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-danger" v-on:click="closeConfirmCollaborationSecond()">Ne</button>
        <button type="button" class="btn btn-success" id="jhi-confirm-delete-advertisement" v-on:click="confirmCollaborationSecond()">
          Da
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
  </div>
</template>

<style>
.unreadMessages {
  /* background-color: #eff5f1 !important; */
  box-shadow: inset 0 0 0 2px #98a7ab;
}
</style>

<script lang="ts" src="./company-threads.component.ts"></script>
