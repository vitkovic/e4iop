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
        <h3 v-text="$t('userManagement.home.adminTitle')" class="mr-3 mb-3 mb-sm-0">Users</h3>
        <b-button 
            variant="primary"
            class="btn btn-sm"
            >
            <font-awesome-icon icon="plus"></font-awesome-icon> 
            <span v-text="$t('userManagement.home.addAdminLabel')">Add a new User</span>
        </b-button>
      </div>
        <div class="custom-table-responsive" v-if="users && users.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('firstName')"><span v-text="$t('userManagement.firstName')">Status</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'firstName'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('lastName')"><span v-text="$t('userManagement.lastName')">Status</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'lastName'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('email')"><span v-text="$t('userManagement.email')">Status</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'email'"></jhi-sort-indicator></th>
                    <!-- <th><span v-text="$t('userManagement.createdBy')">Is Deleted</span></th> -->
                    <!-- <th><span v-text="$t('userManagement.createdDate')">Is Visible</span></th> -->
                    <th><span v-text="$t('userManagement.roles')">Is Deleted</span></th>
                    <th><span v-text="$t('userManagement.status')"></span></th>
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
                    <td>
                        <span v-if="user.activated" v-text="$t('userManagement.activated')" style="color: green;"></span>
                        <span v-if="!user.activated" v-text="$t('userManagement.deactivated')" style="color: red;"></span>
                    </td>

                    <td class="text-right">
                        <div v-if="!user.authorities.includes(authority.CMS_SUPER_ADMIN)">
                            <router-link
                                to=""
                                tag="button"
                                class="btn btn-primary btn-sm edit"
                                >
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button 
                                v-if="user.activated"
                                v-on:click="setActive(user, false)"
                                variant="danger"
                                class="btn btn-sm"
                                >
                                <span v-text="$t('userManagement.action.deactivate')">Deactivate</span>
                            </b-button>
                            <b-button 
                                v-if="!user.activated"
                                v-on:click="setActive(user, true)"
                                variant="success"
                                class="btn btn-sm"
                                >
                                <span v-text="$t('userManagement.action.activate')">Activate</span>
                            </b-button>
                            <b-button 
                                v-on:click="prepareDeleteUserModal(user)"
                                variant="danger"
                                class="btn btn-sm"
                                v-b-modal.deleteUserModal
                                >
                                <font-awesome-icon icon="trash"></font-awesome-icon>
                                <span v-text="$t('entity.action.delete')">Activate</span>
                            </b-button>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>

        <b-modal v-if="userToDelete" ref="deleteUserModal" id="deleteUserModal">
            <span slot="modal-title"
                ><span id="riportalApp.advertisement.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span
            >
            <div class="modal-body">
                <p id="jhi-delete-advertisement-heading" v-text="$t('userManagement.deleteAdmin.question', { 'login': userToDelete.firstName + ' ' + userToDelete.lastName })">
                Are you sure you want to delete this Advertisement from Database?
                </p>
            </div>
            <div slot="modal-footer">
                <button
                type="button"
                class="btn btn-success"
                id="jhi-confirm-delete-advertisement"
                v-text="$t('entity.action.confirm')"
                v-on:click="deleteUser()"
                >
                Delete
                </button>
                <button type="button" class="btn btn-danger" v-text="$t('entity.action.cancel')" v-on:click="closeDeleteUserModal()">Cancel</button>
            </div>
            </b-modal>


        <div v-show="users && users.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>
  
<script lang="ts" src="./cms-b2b-administrators.component.ts"></script>

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
  