<template>
  <div>
    <router-link
      :to="{ name: 'AdvertisementKindCreate' }"
      tag="button"
      id="jh-create-entity"
      class="btn btn-primary float-right jh-create-entity create-advertisement-kind"
    >
      <font-awesome-icon icon="plus"></font-awesome-icon>
      <span v-text="$t('riportalApp.advertisementKind.home.createLabel')">
        Create a new Advertisement Kind
      </span>
    </router-link>
    <h2 id="page-heading" class="mt-5">
      <span v-text="$t('riportalApp.advertisementKind.home.title')" id="advertisement-kind-heading">Advertisement Kinds</span>
    </h2>
    <b-alert
      :show="dismissCountDown"
      dismissible
      :variant="alertType"
      @dismissed="dismissCountDown = 0"
      @dismiss-count-down="countDownChanged"
    >
      {{ alertMessage }}
    </b-alert>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && advertisementKinds && advertisementKinds.length === 0">
      <span v-text="$t('riportalApp.advertisementKind.home.notFound')">No advertisementKinds found</span>
    </div>
    <div class="custom-table-responsive" v-if="advertisementKinds && advertisementKinds.length > 0">
      <table class="table table-striped">
        <thead>
          <tr>
            <th><span v-text="$t('global.field.id')">ID</span></th>
            <th><span v-text="$t('riportalApp.advertisementKind.kind')">Kind</span></th>
            <th><span v-text="$t('riportalApp.advertisementKind.kindSrc')">Kind</span></th>
            <th><span v-text="$t('riportalApp.advertisementKind.kindEn')">Kind</span></th>
            <!-- <th><span v-text="$t('riportalApp.advertisementKind.isVisible')">Is Visible</span></th>
            <th><span v-text="$t('riportalApp.advertisementKind.isDeleted')">Is Deleted</span></th> -->
            <th></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="advertisementKind in advertisementKinds" :key="advertisementKind.id">
            <td>
              <router-link
                :to="{ name: 'AdvertisementKindView', params: { advertisementKindId: advertisementKind.id } }"
                class="text-body"
                >{{ advertisementKind.id }}</router-link
              >
            </td>
            <td>{{ advertisementKind.kind }}</td>
            <td>{{ advertisementKind.kindSrc }}</td>
            <td>{{ advertisementKind.kindEn }}</td>
            <!-- <td>{{ advertisementKind.isVisible }}</td>
            <td>{{ advertisementKind.isDeleted }}</td> -->
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'AdvertisementKindView', params: { advertisementKindId: advertisementKind.id } }"
                  tag="button"
                  class="btn btn-info btn-sm details"
                >
                  <font-awesome-icon icon="eye"></font-awesome-icon>
                  <span v-text="$t('entity.action.view')">View</span>
                </router-link>
                <router-link
                  :to="{ name: 'AdvertisementKindEdit', params: { advertisementKindId: advertisementKind.id } }"
                  tag="button"
                  class="btn btn-primary btn-sm edit"
                >
                  <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                  <span v-text="$t('entity.action.edit')">Edit</span>
                </router-link>
                <b-button v-on:click="prepareRemove(advertisementKind)" variant="danger" class="btn btn-sm" v-b-modal.removeEntity>
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="riportalApp.advertisementKind.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span
      >
      <div class="modal-body">
        <p id="jhi-delete-advertisementKind-heading" v-text="$t('riportalApp.advertisementKind.delete.question', { id: removeId })">
          Are you sure you want to delete this Advertisement Kind?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-advertisementKind"
          v-text="$t('entity.action.delete')"
          v-on:click="removeAdvertisementKind()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./advertisement-kind.component.ts"></script>

<style scoped>
.custom-table-responsive {
  overflow-x: auto;
}

@media (max-width: 1280px) {
  .custom-table-responsive table {
    min-width: 1280px;
  }
}
</style>
