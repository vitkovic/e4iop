<template>
    <div>
      <div v-if="company" class="d-flex mb-3 mt-4">
        <img v-if="company.logo" :src="companyService().retrieveImage(company.logo.filename)" alt="company logo" class="company-logo mr-3" />
        <h2 id="page-heading" class="jh-entity-heading company-title" style="align-self: center;">
          {{ company.name }}
        </h2>
      </div>
      <br />
      <div class="alert alert-warning" v-if="users && users.length === 0">
        <span v-text="$t('riportalApp.portalUser.notFound')">No Users found</span>
      </div>
      <div class="ml-3 mb-3 d-flex flex-column flex-sm-row justify-content-between">
        <h3 v-text="$t('userManagement.home.accountTitle')" class="mr-3 mb-3 mb-sm-0">Users</h3>
        <b-button 
        v-on:click="prepareAddUserModal()"
                                   variant="primary"
                                   class="btn btn-sm"
        v-b-modal.addUserModal>
            <font-awesome-icon icon="plus"></font-awesome-icon> 
            <span v-text="$t('userManagement.home.addLabel')">Add a new User</span>
        </b-button>
      </div>
        <div class="custom-table-responsive" v-if="users && users.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('userManagement.firstName')">Status</span></th>
                    <th><span v-text="$t('userManagement.lastName')">Status</span></th>
                    <th><span v-text="$t('userManagement.email')">Status</span></th>
                    <!-- <th><span v-text="$t('userManagement.createdBy')">Is Deleted</span></th> -->
                    <!-- <th><span v-text="$t('userManagement.createdDate')">Is Visible</span></th> -->
                    <th><span v-text="$t('userManagement.roles')">Is Deleted</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="user in users"
                    :key="user.id">
                    <td>{{ user.firstName }}</td>
                    <td>{{ user.lastName }}</td>
                    <td>{{ user.email }}</td>
                    <!-- <td>{{ user.createdBy }}</td> -->
                    <!-- <td>{{ user.createdDate }}</td> -->
                    <td>                            
                        <div v-for="authority of user.authorities" :key="authority">
                            <span class="badge badge-info">{{ authorityLanguage(authority) }}</span>
                        </div>
                    </td>

                    <td class="text-right">
                        <div v-if="!isAdmin(user)" class="btn-group">
                            <b-button v-on:click="prepareDeleteUserModal(user)"
                                   variant="danger"
                                   class="btn btn-sm"
                                   v-b-modal.deleteUserModal>
                                <font-awesome-icon icon="times"></font-awesome-icon>
                                <span v-text="$t('entity.action.delete')">Delete</span>
                            </b-button>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
            <b-modal ref="deleteUserModal" id="deleteUserModal" v-if="userToDelete" v-bind:title="$t('entity.delete.title')">
                <div class="modal-body">
                    <p id="jhi-delete-user-heading" v-text="$t('userManagement.delete.question', { 'login': ''.concat(userToDelete.firstName, ' ', userToDelete.lastName) })">Are you sure you want to delete this user?</p>
                </div>
                <div slot="modal-footer">
                    <button type="button" class="btn btn-success" id="confirm-delete-user" v-text="$t('entity.action.confirm')" v-on:click="deleteUser()">Delete</button>
                    <button type="button" class="btn btn-danger" v-text="$t('entity.action.cancel')" v-on:click="closeDeleteUserModal()">Cancel</button>
                </div>
            </b-modal>
            <b-modal ref="addUserModal" id="addUserModal" v-bind:title="$t('userManagement.addUser.title')">
                <div class="modal-body">
                    <p style="text-align: justify" v-text="$t('userManagement.addUser.info')">
                        Novog korisika možete dodati tako što ćete u polje ispod uneti email adresu željenog korisnika, nakon čega će na navedeni email biti poslat link za registraciju.
                    </p>
                    <br>
                    <label for="" v-text="$t('userManagement.addUser.enterEmail')">Unesite email adresu</label>
                    <b-form-input
                    type="email"
                    ref="email-address"
                    class="form-control mb-3"
                    name="email-address"
                    id="email-address"
                    v-model="emailAddress"
                    ></b-form-input>
                </div>
                <div slot="modal-footer">
                    <button type="button" class="btn btn-success" id="confirm-delete-user" v-text="$t('entity.action.send')" v-on:click="addUser()" :disabled="!$v.emailAddress.email || !$v.emailAddress.required">Delete</button>
                    <button type="button" class="btn btn-danger" v-text="$t('entity.action.cancel')" v-on:click="closeAddUserModal()">Cancel</button>
                </div>
            </b-modal>
        </div>
    </div>
</template>
  
  <script lang="ts" src="./company-portal-users.component.ts"></script>
  
  <style scoped>
  .company-logo {
    height: 75px;
  }
  
  .custom-table-responsive {
    overflow-x: auto;
  }
  
  @media (max-width: 1280px) {
    .custom-table-responsive table {
      min-width: 1280px;
    }
  }
  
  @media screen and (max-width: 578px) {
    .company-title {
      font-size: 2rem;
    }
  }
  </style>
  