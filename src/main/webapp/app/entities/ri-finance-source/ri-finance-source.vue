<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.riFinanceSource.home.title')" id="ri-finance-source-heading">Ri Finance Sources</span>
            <router-link :to="{ name: 'RiFinanceSourceCreate' }" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-ri-finance-source">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span v-text="$t('riportalApp.riFinanceSource.home.createLabel')">Create a new Ri Finance Source</span>
            </router-link>
        </h2>
        <b-alert :show="dismissCountDown"
            dismissible
            :variant="alertType"
            @dismissed="dismissCountDown = 0"
            @dismiss-count-down="countDownChanged">
            {{ alertMessage }}
        </b-alert>
        <br />
        <div class="alert alert-warning" v-if="!isFetching && riFinanceSources && riFinanceSources.length === 0">
            <span v-text="$t('riportalApp.riFinanceSource.home.notFound')">No riFinanceSources found</span>
        </div>
        <div class="table-responsive" v-if="riFinanceSources && riFinanceSources.length > 0">
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th><span v-text="$t('global.field.id')">ID</span></th>
                        <th><span v-text="$t('riportalApp.riFinanceSource.sourceSr')">Source Sr</span></th>
                        <th><span v-text="$t('riportalApp.riFinanceSource.sourceSrc')">Source Src</span></th>
                        <th><span v-text="$t('riportalApp.riFinanceSource.sourceEn')">Source En</span></th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="riFinanceSource in riFinanceSources" :key="riFinanceSource.id">
                        <td>
                            <router-link :to="{ name: 'RiFinanceSourceView', params: { riFinanceSourceId: riFinanceSource.id } }">
                                {{ riFinanceSource.id }}
                            </router-link>
                        </td>
                        <td>{{ riFinanceSource.sourceSr }}</td>
                        <td>{{ riFinanceSource.sourceSrc }}</td>
                        <td>{{ riFinanceSource.sourceEn }}</td>
                        <td class="text-right">
                            <div class="btn-group">
                                <router-link :to="{ name: 'RiFinanceSourceView', params: { riFinanceSourceId: riFinanceSource.id } }" tag="button" class="btn btn-info btn-sm details">
                                    <font-awesome-icon icon="eye"></font-awesome-icon>
                                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                                </router-link>
                                <router-link :to="{ name: 'RiFinanceSourceEdit', params: { riFinanceSourceId: riFinanceSource.id } }" tag="button" class="btn btn-primary btn-sm edit">
                                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                                </router-link>
                                <b-button @click="prepareRemove(riFinanceSource)"
                                        variant="danger"
                                        class="btn btn-sm"
                                        v-b-modal.removeEntity>
                                    <font-awesome-icon icon="times"></font-awesome-icon>
                                    <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                                </b-button>
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <b-modal ref="removeEntity" id="removeEntity">
            <span slot="modal-title">
                <span id="riportalApp.riFinanceSource.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span>
            </span>
            <div class="modal-body">
                <p id="jhi-delete-riFinanceSource-heading" v-text="$t('riportalApp.riFinanceSource.delete.question', { 'id': removeId })">
                    Are you sure you want to delete this Ri Finance Source?
                </p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" @click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-riFinanceSource" v-text="$t('entity.action.delete')" @click="removeRiFinanceSource()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./ri-finance-source.component.ts">
</script>
