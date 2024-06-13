<template>
    <div>
      <div class="row justify-content-center">
          <div class="col-md-8">
            <div v-if="success">
                <h1>
                    <span><font-awesome-icon icon="check" style="color: green;"/></span>
                    <span>{{ message }}</span>
                    <span v-if="meetingParticipant.meeting"><strong>{{ meetingParticipant.meeting.title }}</strong></span> 
                </h1>
            </div>
            <div v-else-if="error">
                <h1>
                    <span>{{ message }}</span>
                </h1>
            </div>
            <div v-else>
                <h1>
                    <span v-text="'Sistemska greška'"></span>
                </h1>
            </div>
            <br>
            <br>
            <div v-if="companyId">
                <p>
                    <span v-text="'Sve zakazane sastanke možete pogledati na stranici'"></span>
                    <b-link class="text-body" :to="{name: 'CompanyCalendar', params: {companyId: companyId}}">	
						<span v-text="'Kalendar'">Kalendar</span>
					</b-link>
                </p>
            </div>

            
          </div>
      </div>
    </div>
    </template>
    
    <script lang="ts" src="./meeting-accept.component.ts">
    </script>
    