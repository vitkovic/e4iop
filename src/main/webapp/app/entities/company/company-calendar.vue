<template>
  <div class="row justify-content-center">
    <div class="col-12 col-md-8">
      <div v-if="company">
        <br />
        <div class="d-flex mb-3">
          <img
            v-if="company.logo"
            :src="companyService().retrieveImage(company.logo.filename)"
            alt="company logo"
            style="height: 100px; margin-right: 1rem;"
          />
          <h2 class="jh-entity-heading" style="align-self: center; text-wrap: nowrap;">
            {{ company.name }}
          </h2>
        </div>
        <br />
        <div class="fc fc-media-screen fc-direction-ltr fc-theme-standard" style="position: relative; height: 60vh; overflow-y: auto;">
          <!-- <div class="calendar-wrapper "> -->
          <FullCalendar ref="fullCalendar" :options="calendarOptions" />
        </div>
      </div>
    </div>

    <!-- PRVI MODAL PRIHVATI POZIV ZA SASTANAK--- RESPONSIVE JE, ПРЕВЕДЕНО ЈЕ -->

    <b-modal v-if="selectedEvent" ref="acceptMeetingModal" id="acceptMeetingModal">
      <div class="modal-body">
        <p>
          <span v-text="$t('riportalApp.calendar.acceptMeetingModal.question')">Da li želite da prihvatite poziv za sastanak?</span>
          <span
            ><b>{{ selectedEvent.title }}</b></span
          >
          <span v-text="'?'"></span>
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-success" v-text="$t('entity.action.confirm')" v-on:click="acceptMeeting()">Cancel</button>
        <button type="button" class="btn btn-danger" v-text="$t('entity.action.cancel')" v-on:click="closeAcceptMeetingModal()"></button>
      </div>
    </b-modal>

    <!-- PRVI MODAL END  -->

    <!-- DRUGI MODAL ODBIJ POZIV ZA SASTANAK --- RESPONSIVE JE ПРЕВЕДЕН ЈЕ-->

    <b-modal v-if="selectedEvent" ref="rejectMeetingModal" id="rejectMeetingModal">
      <div class="modal-body">
        <p>
          <span v-text="$t('riportalApp.calendar.rejectMeetingModal.question')">Da li želite da otkažete poziv za sastanak?</span>
          <span
            ><b>{{ selectedEvent.title }}</b></span
          >
          <span v-text="'?'"></span>
        </p>

        <label for="" v-text="$t('riportalApp.calendar.rejectMeetingModal.cancelationReason')"></label>
        <b-form-textarea v-model="rejectMeetingComment.comment" class="mb-3" id="" cols="30" rows="5"></b-form-textarea>

        <label for="" v-text="$t('riportalApp.calendar.rejectMeetingModal.newAppointment')"></label>
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

    <!-- DRUGI MODAL END -->

    <!-- TRECI MODAL OVO JE PREGLED KREIRANOG SASTANKA---  RESPONSIVE JE PREVEDEN JE-->

    <b-modal v-if="selectedEvent" ref="viewMeetingModal" id="viewMeetingModal">
      <span slot="modal-title">{{ selectedEvent.title }}</span>
      <div class="modal-body">
        <div v-if="selectedEvent.startDate === selectedEvent.endDate">
          <p>
            <span>{{ selectedEvent.startDate }}</span>
            <span>{{ selectedEvent.startTime }} - {{ selectedEvent.endTime }}</span>
          </p>
        </div>
        <div v-else>
          <p>
            <span>{{ selectedEvent.startDate }}</span>
            <span>{{ selectedEvent.startTime }}</span>
          </p>
          <p>
            <span>{{ selectedEvent.endDate }}</span>
            <span>{{ selectedEvent.endTime }}</span>
          </p>
        </div>
        <p v-if="selectedEvent.advertisement">
          <b v-text="$t('riportalApp.calendar.viewMeetingModal.advertisement')">Oglas: </b>
          <span>{{ selectedEvent.advertisement.title }}</span>
        </p>
        <p v-if="selectedEvent.location">
          <b v-text="$t('riportalApp.calendar.viewMeetingModal.location')">Lokacija: </b>
          <span>{{ selectedEvent.location }}</span>
        </p>
        <p v-if="selectedEvent.description">
          <b v-text="$t('riportalApp.calendar.viewMeetingModal.description')">Opis: </b>
          <span>{{ selectedEvent.description }}</span>
        </p>
        <hr />

        <div v-if="selectedEvent.organizer">
          <label for="" v-text="$t('riportalApp.calendar.createModal.organizer')"></label>
          <div v-if="selectedEvent.organizer" class="d-flex align-items-center">
            <div v-if="selectedEvent.organizer.company.logo" class="company-logo-container position-relative">
              <img
                :src="companyService().retrieveImage(selectedEvent.organizer.company.logo.filename)"
                alt="company logo"
                style="width: 100%; max-height: 100%;"
                class="company-logo"
              />
            </div>
            <div v-else class="placeholder-logo">{{ getCompanyInitials(selectedEvent.organizer.company) }}</div>
            <span>{{ selectedEvent.organizer.company.name }}</span>
          </div>
          <hr />
        </div>

        <div v-if="selectedEvent.advertiser">
          <label for="" v-text="$t('riportalApp.calendar.createModal.advertiser')"></label>
          <div class="d-flex align-items-center justify-content-between">
            <div class="d-flex align-items-center">
              <div v-if="selectedEvent.advertiser.company.logo" class="company-logo-container position-relative">
                <img
                  :src="companyService().retrieveImage(selectedEvent.advertiser.company.logo.filename)"
                  alt="company logo"
                  style="width: 100%; max-height: 100%;"
                  class="company-logo"
                />
              </div>
              <div v-else class="placeholder-logo">{{ getCompanyInitials(selectedEvent.advertiser.company) }}</div>
              <span>{{ selectedEvent.advertiser.company.name }}</span>
            </div>
            <div>
              <span>{{ selectedEvent.advertiser.status.statusSr }}</span>
            </div>
          </div>
          <hr />
        </div>

        <div v-if="selectedEvent.otherParticipants.length > 0">
          <label v-text="$t('riportalApp.calendar.viewMeetingModal.otherCompanies')"></label>
          <div v-for="participant in selectedEvent.otherParticipants" class="d-flex align-items-center justify-content-between mb-3">
            <div class="d-flex align-items-center">
              <div v-if="participant.company.logo" class="company-logo-container position-relative">
                <img
                  :src="companyService().retrieveImage(participant.company.logo.filename)"
                  alt="company logo"
                  style="width: 100%; max-height: 100%;"
                  class="company-logo"
                />
              </div>
              <div v-else class="placeholder-logo">{{ getCompanyInitials(participant.company) }}</div>
              <span>{{ participant.company.name }}</span>
            </div>
            <div>
              <span>{{ participant.status.statusSr }}</span>
            </div>
          </div>
        </div>

        <div v-if="selectedEvent.allNonB2BMeetingParticipants.length > 0">
          <hr />
          <label v-text="$t('riportalApp.calendar.viewMeetingModal.outsideB2BPortal')"></label>
          <ul style="padding-left: 18px;">
            <li v-for="participant in selectedEvent.allNonB2BMeetingParticipants" :key="participant.email" class="mb-2">
              <span>{{ participant.email }}</span>
            </li>
          </ul>
        </div>
      </div>

      <div slot="modal-footer" style="width: 100%;">
        <div class="d-flex mb-2" style="width: 100%; justify-content: flex-end;">
          <button
            v-if="!isThereMeetingResponseForCurrentCompany(selectedEvent)"
            type="button"
            class="btn btn-secondary mr-2"
            v-on:click="prepareAcceptMeetingModal()"
            style="width: 30%;"
          >
            <span><font-awesome-icon icon="check" style="color: green;" /></span>
            <span v-text="$t('entity.action.accept')"></span>
          </button>
          <button
            v-if="!isThereMeetingResponseForCurrentCompany(selectedEvent)"
            type="button"
            class="btn btn-secondary"
            v-on:click="prepareRejectMeetingModal()"
            style="width: 30%;"
          >
            <span><font-awesome-icon icon="times" style="color: red;" /></span>
            <span v-text="$t('entity.action.cancel')"></span>
          </button>
        </div>
        <div class="d-flex" style="width: 100%; justify-content: flex-end;">
          <button
            v-if="selectedEvent.organizer && selectedEvent.organizer.company.id == companyId"
            type="button"
            class="btn btn-primary mr-2"
            v-text="$t('entity.action.edit')"
            v-on:click="prepareEditMeetingModal()"
            style="width: 30%;"
          >
            Izmeni
          </button>
          <button
            type="button"
            class="btn btn-primary mr-2"
            v-text="$t('riportalApp.meetingParticipant.notes')"
            v-on:click="prepareMeetingNotesModal()"
            style="width: 30%;"
          >
            Zapisnik
          </button>
          <button type="button" class="btn btn-primary" v-text="$t('riportalApp.calendar.ics')" v-on:click="createICS(selectedEvent)" style="width: 30%;">
            Preuzmi ICS
          </button>
          <!-- <button type="button" class="btn btn-danger" v-text="$t('entity.action.remove')" v-on:click="prepareRemoveMeetingModal(selectedEvent.id)">Obriši</button> -->
          <!-- <button type="button" class="btn btn-danger" v-text="$t('entity.action.cancel')" v-on:click="closeViewMeetingModal()">Cancel</button> -->
        </div>
      </div>
    </b-modal>

    <!-- TRECI END -->

    <!-- CETRVTI MODAL KREIRAJ SASTANAK -  RESPONSIVE JE PREVEDEN JE-->

    <b-modal ref="createMeetingModal" id="createMeetingModal" size="lg">
      <span slot="modal-title"
        ><span id="riportalApp.researchInfrastructure.calendar" v-text="$t('riportalApp.calendar.createModal.titleModal')"></span
      ></span>
      <div class="d-flex">
        <div class="modal-body" style="border-right: 1px solid #ccc; width: 45%; padding-right: 5%;">
          <div class="mb-3">
            <b-input v-model="meetingEvent.title" :placeholder="$t('riportalApp.calendar.createModal.placeholderTitle')"></b-input>
          </div>

          <div class="d-flex flex-column flex-lg-row mb-2 mb-lg-0">
            <b-form-datepicker
              class="datePicker"
              v-model="meetingEvent.startDate"
              :date-format-options="{ year: 'numeric', month: 'numeric', day: 'numeric' }"
              placeholder="Izaberite datum početka..."
              @input="updateBFormCalendarEndDate(meetingEvent, 'startDate', 'endDate')"
            ></b-form-datepicker>
            <b-form-timepicker class="timePicker" minutes-step="15" v-model="meetingEvent.startTime"></b-form-timepicker>
          </div>

          <div class="d-flex flex-column flex-lg-row mb-3">
            <b-form-datepicker
              class="datePicker"
              v-model="meetingEvent.endDate"
              :date-format-options="{ year: 'numeric', month: 'numeric', day: 'numeric' }"
              :min="meetingEvent.startDate"
            ></b-form-datepicker>
            <b-form-timepicker class="timePicker" minutes-step="15" v-model="meetingEvent.endTime"></b-form-timepicker>
          </div>

          <b-input
            v-model="meetingEvent.location"
            class="mb-3"
            :placeholder="$t('riportalApp.calendar.createModal.placeholderLocation')"
          ></b-input>

          <b-form-textarea
            v-model="meetingEvent.description"
            class="mb-3"
            :placeholder="$t('riportalApp.calendar.createModal.placeholderDescription')"
          ></b-form-textarea>
        </div>

        <div class="modal-body" style="width: 45%; padding-left: 5%;">
          <label for="" v-text="$t('riportalApp.calendar.createModal.organizer')">Organizator</label>
          <div v-if="company" class="d-flex align-items-center">
            <div v-if="company.logo" class="company-logo-container position-relative">
              <img
                :src="companyService().retrieveImage(company.logo.filename)"
                alt="company logo"
                style="width: 100%; max-height: 100%;"
                class="company-logo"
              />
            </div>
            <div v-else class="placeholder-logo">{{ getCompanyInitials(company) }}</div>
            <span>{{ company.name }}</span>
          </div>
          <hr />

          <label for="" v-text="$t('riportalApp.calendar.createModal.participants')">Dodajte druge kompanije sa B2B portala</label>
          <input
            type="text"
            ref="company-name"
            class="form-control mb-3"
            name="company-name"
            id="company-name"
            :placeholder="$t('riportalApp.calendar.createModal.placeholderCompany')"
            @keyup="getCompaniesBySearchText()"
            @focusout="toggleSearchList($event, 'showCompaniesSearch')"
            @focusin="toggleSearchList($event, 'showCompaniesSearch')"
            v-model="companySearchText"
            autocomplete="off"
          />
          <div
            class="form-control"
            style="text-align: left; height: 150px; overflow-y: scroll; position: absolute; z-index: 9999;"
            v-if="companiesSearch.length && showCompaniesSearch"
          >
            <ul class="list-group" @click.stop>
              <div v-for="company in companiesSearch" :key="company.id">
                <a class="list-group-item" @mousedown="addToCompaniesMeetingParticipants(company)">
                  <span>{{ company.name }}</span>
                </a>
              </div>
            </ul>
          </div>

          <div
            v-for="company in companiesMeetingParticipants"
            :key="company.id"
            class="d-flex align-items-center justify-content-between mb-3"
          >
            <div class="d-flex align-items-center">
              <div v-if="company.logo" class="company-logo-container position-relative">
                <img
                  :src="companyService().retrieveImage(company.logo.filename)"
                  alt="company logo"
                  style="width: 100%; max-height: 100%;"
                  class="company-logo"
                />
              </div>
              <div v-else class="placeholder-logo">{{ getCompanyInitials(company) }}</div>
              <span>{{ company.name }}</span>
            </div>
            <b-button @click="removeFromCompaniesMeetingParticipants(company)" variant="primary" class="close removeButton">x</b-button>
          </div>

          <hr />
          <label for="" v-text="$t('riportalApp.calendar.createModal.noB2BParticipants')">Pozovite učesnike koji nisu na B2B portalu</label>
          <b-input-group>
            <b-form-input
              type="email"
              ref="email-address"
              class="form-control mb-3"
              name="email-address"
              id="email-address"
              :placeholder="$t('riportalApp.calendar.createModal.placeholderNoB2BParcitipants')"
              v-model="nonB2BMeetingParticipantEmail"
              @keyup.enter="addNonB2BMeetingParticipant()"
            ></b-form-input>
            <div>
              <b-button
                @click="addNonB2BMeetingParticipant()"
                variant="primary"
                :disabled="!$v.nonB2BMeetingParticipantEmail.email || !$v.nonB2BMeetingParticipantEmail.required"
                ><span class="d-inline d-sm-none"><font-awesome-icon icon="plus"></font-awesome-icon></span
                ><span class="d-none d-sm-inline">{{ $t('entity.action.add') }}</span>
              </b-button>
            </div>
          </b-input-group>
          <small class="form-text text-danger" v-if="!isEmailValid" v-text="$t('riportalApp.calendar.createModal.errorEmail')"
            >Email adresa nije ispravna.
          </small>
          <div v-for="email in nonB2BParticipantsEmails" :key="email" class="d-flex align-items-center justify-content-between mb-3">
            <div class="emailSection">
              <span class="emailText">{{ email }}</span>
              <b-button @click="removeNonB2BMeetingParticipant(email)" variant="primary" class="close removeButton">x</b-button>
            </div>
          </div>
        </div>
      </div>
      <div slot="modal-footer">
        <button
          type="button"
          class="btn btn-success"
          v-text="$t('riportalApp.calendar.createModal.makeAppointment')"
          v-on:click="createMeeting()"
        >
          Zakaži sastanak
        </button>
        <button type="button" class="btn btn-danger" v-text="$t('entity.action.cancel')" v-on:click="closeCreateMeetingModal()">
          Cancel
        </button>
      </div>
    </b-modal>

    <!-- PETI MODAL IZMENI SASTANAK - RESPONSIVE JE PREVEDEN JE-->

    <b-modal v-if="selectedEvent" ref="editMeetingModal" id="editMeetingModal" size="lg">
      <div slot="modal-title" v-if="selectedEvent.advertisement">
        <span v-text="$t('riportalApp.calendar.editMeetingModal.titleModal')"></span>
        <span>{{ selectedEvent.advertisement.title }}</span>
      </div>
      <div slot="modal-title" v-else>
        <span id="riportalApp.researchInfrastructure.calendar" v-text="$t('riportalApp.calendar.editMeetingModal.titleModalSimple')"></span>
      </div>

      <div class="d-flex">
        <div class="modal-body overflow-auto" style="border-right: 1px solid #ccc; width: 45%; padding-right: 5%;">
          <div class="mb-3">
            <b-input v-model="selectedEvent.title" :placeholder="$t('riportalApp.calendar.createModal.placeholderTitle')"></b-input>
          </div>

          <div class="d-flex flex-column flex-lg-row mb-2 mb-lg-0">
            <b-form-datepicker
              class="datePicker"
              v-model="selectedEvent.startStr"
              :date-format-options="{ year: 'numeric', month: 'numeric', day: 'numeric' }"
              placeholder="Izaberite datum početka..."
              @input="updateBFormCalendarEndDate(selectedEvent, 'startStr', 'endStr')"
            ></b-form-datepicker>
            <b-form-timepicker class="timePicker" minutes-step="15" v-model="selectedEvent.startTime"></b-form-timepicker>
          </div>

          <div class="d-flex flex-column flex-lg-row mb-3">
            <b-form-datepicker
              class="datePicker"
              v-model="selectedEvent.endStr"
              :date-format-options="{ year: 'numeric', month: 'numeric', day: 'numeric' }"
              :min="selectedEvent.startStr"
            ></b-form-datepicker>
            <b-form-timepicker class="timePicker" minutes-step="15" v-model="selectedEvent.endTime"></b-form-timepicker>
          </div>

          <b-input v-model="selectedEvent.location" class="mb-3" :placeholder="$t('riportalApp.calendar.createModal.placeholderLocation')"></b-input>

          <b-form-textarea v-model="selectedEvent.description" class="mb-3" :placeholder="$t('riportalApp.calendar.createModal.placeholderDescription')"></b-form-textarea>
        </div>

        <div class="modal-body overflow-auto" style="width: 45%; padding-left: 5%;">
          <label for="" v-text="$t('riportalApp.calendar.createModal.participants')">Dodajte druge učesnike</label>
          <input
            type="text"
            ref="company-name"
            class="form-control mb-3"
            name="company-name"
            id="company-name"
            placeholder="Potražite kompaniju..."
            @keyup="getCompaniesBySearchText()"
            @focusout="toggleSearchList($event, 'showCompaniesSearch')"
            @focusin="toggleSearchList($event, 'showCompaniesSearch')"
            v-model="companySearchText"
            autocomplete="off"
          />
          <div
            class="form-control"
            style="text-align: left; height: 150px; overflow-y: scroll; position: absolute; z-index: 9999;"
            v-if="companiesSearch.length && showCompaniesSearch"
          >
            <ul class="list-group" @click.stop>
              <div v-for="company in companiesSearch" :key="company.id">
                <a class="list-group-item" @mousedown="addToCompaniesMeetingParticipants(company)">
                  <span>{{ company.name }}</span>
                </a>
              </div>
            </ul>
          </div>

          <div v-for="company in companiesMeetingParticipants" :key="company.id" class="mb-3">
            <div v-if="selectedEvent.organizer && company.id == selectedEvent.organizer.company.id" class="mb-2">
              <span v-text="$t('riportalApp.calendar.createModal.organizer')"></span>
            </div>
            <div v-else-if="selectedEvent.advertiser && company.id == selectedEvent.advertiser.company.id" class="mb-2">
              <span v-text="$t('riportalApp.calendar.createModal.advertiser')"></span>
            </div>

            <div class="d-flex align-items-center justify-content-between">
              <div class="d-flex align-items-center">
                <div v-if="company.logo" class="company-logo-container position-relative">
                  <img
                    :src="companyService().retrieveImage(company.logo.filename)"
                    alt="company logo"
                    style="width: 100%; max-height: 100%;"
                    class="company-logo"
                  />
                </div>
                <div v-else class="placeholder-logo">{{ getCompanyInitials(company) }}</div>
                <span>{{ company.name }}</span>
              </div>
              <div v-if="!selectedEvent.organizer || company.id != selectedEvent.organizer.company.id">
                <div v-if="!selectedEvent.advertiser || company.id != selectedEvent.advertiser.company.id">
                  <b-button @click="removeFromCompaniesMeetingParticipants(company)" variant="primary" class="close removeButton"
                    >x</b-button
                  >
                </div>
              </div>
            </div>
          </div>

          <hr />
          <label for="" v-text="$t('riportalApp.calendar.createModal.noB2BParticipants')">Pozovite učesnike koji nisu na B2B portalu</label>
          <b-input-group>
            <b-form-input
              type="email"
              ref="email-address"
              class="form-control mb-3"
              name="email-address"
              id="email-address"
              :placeholder="$t('riportalApp.calendar.createModal.placeholderNoB2BParcitipants')"
              v-model="nonB2BMeetingParticipantEmail"
              @keyup.enter="addNonB2BMeetingParticipant()"
            ></b-form-input>
            <div>
              <b-button
                @click="addNonB2BMeetingParticipant()"
                variant="primary"
                :disabled="!$v.nonB2BMeetingParticipantEmail.email || !$v.nonB2BMeetingParticipantEmail.required"
                ><span class="d-inline d-sm-none"><font-awesome-icon icon="plus"></font-awesome-icon></span
                ><span class="d-none d-sm-inline">{{ $t('entity.action.add') }}</span>
              </b-button>
            </div>
          </b-input-group>
          <small class="form-text text-danger" v-if="!isEmailValid" v-text="$t('riportalApp.calendar.createModal.errorEmail')"
            >Email adresa nije ispravna.
          </small>
          <div v-for="email in nonB2BParticipantsEmails" :key="email" class="d-flex align-items-center justify-content-between mb-3">
            <div class="emailSection">
              <span class="emailText">{{ email }}</span>
              <b-button @click="removeNonB2BMeetingParticipant(email)" variant="primary" class="close removeButton">x</b-button>
            </div>
          </div>
        </div>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-success" v-text="$t('entity.action.save')" v-on:click="editMeeting()">Sačuvaj</button>
        <button type="button" class="btn btn-danger" v-text="$t('entity.action.cancel')" v-on:click="closeEditMeetingModal()">
          Cancel
        </button>
      </div>
    </b-modal>

    <!-- PETI MODAL END -->

    <!-- SETSI MODAL OBRISI KREIRAN SASTANAK DUGME OBRISI JE ZAKOMENTARISANO  - RESPONSIVE JE PREVEDEN JE-->

    <b-modal v-if="meetingToRemove" ref="removeMeetingModal" id="removeMeetingModal">
      <div class="modal-body">
        <p id="jhi-delete-thread-heading" v-text="$t('riportalApp.calendar.removeMeetingModal.question')">
          Da li želite da uklonite sastanak iz kalendara?<span>{{ meetingToRemove.title }}</span>
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-success" v-text="$t('entity.action.confirm')" v-on:click="removeMeeting()">Potvrdi</button>
        <button type="button" class="btn btn-danger" v-text="$t('entity.action.cancel')" v-on:click="closeRemoveMeetingModal()">
          Canel
        </button>
      </div>
    </b-modal>

    <!-- SESTI MODAL END -->

    <!-- SEDMI MODAL ZA ZAPISNIK -  RESPONSIVE JE PREVEDEN JE-->

    <b-modal v-if="selectedEvent" ref="meetingNotesModal" id="meetingNotesModal">
      <span slot="modal-title">{{ selectedEvent.title }}</span>
      <div class="modal-body">
        <h5 id="jhi-delete-thread-heading" class="mb-4">
          <span v-text="$t('riportalApp.meetingParticipant.notes')"></span>
          <span class="text-info" v-b-tooltip.hover.v-info :title="$t('riportalApp.meetingParticipant.notesInfo')"
            ><font-awesome-icon icon="question-circle"></font-awesome-icon
          ></span>
        </h5>
        <!-- <b-form-textarea v-model="meetingNotes" class="mb-3" id="" cols="30" rows="10"> </b-form-textarea> -->
        <vue-editor v-model="meetingNotes" :editor-toolbar="customToolbar" />
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-success" v-text="$t('entity.action.save')" v-on:click="updateMeetingNotes()">
          Potvrdi
        </button>
        <button type="button" class="btn btn-danger" v-text="$t('entity.action.close')" v-on:click="closeMeetingNotesModal()">Canel</button>
      </div>
    </b-modal>

    <!-- SEDMI MODAL END  -->
  </div>
</template>

<style>
.jh-entity-details > dd {
  justify-self: left;
  margin-left: 5rem !important;
}

.badge-section {
  margin-top: 2rem;
  margin-bottom: 2rem;
}

.badge-section > h3 {
  margin-bottom: 1rem;
}

.badge-description {
  display: none;
  padding: 0.5rem;
  border-radius: 0.5rem;
  background-color: rgb(128, 128, 128);
  color: white;
  position: absolute;
  bottom: 100%;
  left: 100%;
  z-index: 10;
  width: 300%;
}

.badge-wrapper {
  position: relative;
  display: inline-block;
}

.badge-wrapper:hover .badge-description {
  display: block;
}

.startech-link,
.startech-link:link,
.startech-link:visited,
.startech-link:hover,
.startech-link:active {
  color: blue;
  text-decoration: none;
}

.img-thumbnail {
  width: 100%; /* Ensure images fill their container */
  height: 200px; /* Maintain aspect ratio */
  cursor: pointer; /* Change cursor to pointer on hover */
  padding: 0;
  transition: all 0.4s;
  box-shadow: 0 0.6rem 1.2rem rgba(0, 0, 0, 0.075);
  object-fit: cover; /* Ensure images fill the entire card */
}

.img-thumbnail:hover {
  transform: translateY(-0.3rem);
  box-shadow: 0 0.8rem 1.6rem rgba(0, 0, 0, 0.06);
}

h1,
h2 {
  color: #004b90;
}

.fc {
  /* the calendar root */
  width: 100%;
  height: 100%;
  margin: 0 auto;
  /* overflow-x: auto; */
}

.timePicker {
  width: 30%;
}

.datePicker {
  width: 70%;
}

.emailSection {
  width: 100%;
  white-space: normal;
}

.emailText {
  word-break: break-all;
  overflow-wrap: break-word;
}

.removeButton:hover {
  background-color: #fff;
}

.timePicker button {
  padding-right: 2px;
}

.fc-event-main-frame {
  flex-direction: column;
  }
   
.fc-event-time {
  font-weight: 400;
  font-size: 12px;
}

.fc-timegrid-event .fc-event-time{
  white-space: normal;

}

.fc-event-title {
  font-size: 12px;
}

.company-logo-container {
  width: 40px;
  height: 40px;
  margin-right: 1rem;
  border-radius: 50%; /* Make the image circular */
  object-fit: cover; /* Ensure the image covers the whole circle */
  border: 2px solid #ccc;
  align-content: center;
}

.company-logo {
  border-radius: 50%; /* Make the image circular */
}

.placeholder-logo {
  width: 40px;
  height: 40px;
  margin-right: 1rem;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #ccc;
  border-radius: 50%;
  color: black;
  text-align: center; /* Center text inside the div */
}

@media (max-width: 992px) {
  .timePicker {
    width: 100%;
  }

  .datePicker {
    width: 100%;
  }
}

@media (max-width: 900px) {
  .fc {
    margin: 0;
  }

  .fc .fc-toolbar {
    flex-direction: column;
    row-gap: 10px;
    width: 100%;
  }
}

@media (max-width: 576px) {
  .fc-h-event .fc-event-main-frame {
    /* flex-direction: column; */
    font-size: 8px;
  }

  .fc-event-main {
    font-size: 10px;
  }

  .company-logo-container {
    width: 20px;
    height: 20px;
    margin-right: 5px;
    border-radius: 50%; /* Make the image circular */
    object-fit: cover; /* Ensure the image covers the whole circle */
    border: 1px solid #ccc;
    align-content: center;
  }

  .company-logo {
    vertical-align: baseline;
  }

  .placeholder-logo {
    width: 20px;
    height: 20px;
    margin-right: 5px;
    display: flex;
    align-items: center;
    justify-content: center;
    background-color: #ccc;
    border-radius: 50%;
    color: black;
    text-align: center; /* Center text inside the div */
    overflow: hidden;
  }

  /* .fc-event-title-container {
    display: none;
  } VIDI SA VLADOM */
}

/* .fc-view-container {
  width: 100%;
} */
</style>

<script lang="ts" src="./company-calendar.component.ts"></script>
