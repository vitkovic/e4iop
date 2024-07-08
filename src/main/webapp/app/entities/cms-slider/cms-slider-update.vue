<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 v-if="cmsSliderTitleHasID === false" id="riportalApp.cmsQuestion.home.createTitleLabel" v-text="$t('riportalApp.cmsSlider.home.createTitleLabel')">Create a CmsQuestion</h2>
                <h2 v-else id="riportalApp.cmsQuestion.home.editTitleLabel" v-text="$t('riportalApp.cmsSlider.home.editTitleLabel')">Edit a CmsQuestion</h2>
                <div>
                    <div class="form-group" v-if="cmsSlider.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="cmsSlider.id" readonly />
                    </div>

                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.cmsSlider.title')" for="cms-slider-title">Title</label>
                        <input type="text" class="form-control" name="title" id="cms-slider-title"
                            :class="{'valid': !$v.cmsSlider.title.$invalid, 'invalid': $v.cmsSlider.title.$invalid }" v-model="$v.cmsSlider.title.$model"  required/>
                        <div v-if="$v.cmsSlider.title.$anyDirty && $v.cmsSlider.title.$invalid">
                            <small class="form-text text-danger" v-if="!$v.cmsSlider.title.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.cmsSlider.link')" for="cms-slider-link">Link</label>
                        <input type="text" class="form-control" name="link" id="cms-slider-link"
                            :class="{'valid': !$v.cmsSlider.link.$invalid, 'invalid': $v.cmsSlider.link.$invalid }" v-model="$v.cmsSlider.link.$model" placeholder="Uneti link sa http:// ili https://" required/>
                        <div v-if="$v.cmsSlider.link.$anyDirty && $v.cmsSlider.link.$invalid">
                            <small class="form-text text-danger" v-if="!$v.cmsSlider.link.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.cmsSlider.newTab')" for="cms-slider-newTab">New Tab</label>
                        <input type="checkbox" class="form-check" name="newTab" id="cms-slider-newTab" required
                            :class="{'valid': !$v.cmsSlider.newTab.$invalid, 'invalid': $v.cmsSlider.newTab.$invalid }" v-model="$v.cmsSlider.newTab.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.cmsSlider.ordinalNumber')" for="cms-slider-ordinalNumber">Ordinal Number</label>
                        <input type="number" class="form-control" name="ordinalNumber" id="cms-slider-ordinalNumber"
                            :class="{'valid': !$v.cmsSlider.ordinalNumber.$invalid, 'invalid': $v.cmsSlider.ordinalNumber.$invalid }" v-model.number="$v.cmsSlider.ordinalNumber.$model" />
                    </div>

                    <div class="documents-section">
                        <div class="form-group row">
                            <div class="col-xs-12 col-sm-6">
                            <label
                                class="form-control-label position-relative font-weight-bold"
                                v-text="$t('riportalApp.advertisement.upload.imgUpload')"
                            ></label>
                            <b-form-file
                                style="margin-bottom: 5px;"
                                v-model="sliderImage"
                                class="customPlaceholder"
                                :state="Boolean(sliderImage)"
                                :placeholder="placeholdertext"
                                drop-placeholder="Drop file here..."
                                :browse-text="browseButtonText"
                                accept=".jpg, .jpeg, .png, .svg"
                                :file-name-formatter="formatNames"
                            >
                            </b-form-file>
                            </div>
                            <div class="col-xs-12 col-sm-6">
                            <div v-if="cmsSlider.image">
                                <p class="logo-img-label font-weight-bold" v-text="$t('riportalApp.advertisement.upload.currentImg')">Current logo:</p>
                                <div class="mb-3 pl-1">
                                <img :src="retrieveFile(cmsSlider.image)" width="50" height="50" />
                                <!-- {{ company.logo.filename }} -->
                                <button type="button" class="btn btn-sm btn-danger ml-3" v-on:click="openDeleteSliderImageModal()" v-b-modal.deleteSliderImageModal>
                                    <span v-text="$t('entity.action.delete')">Save</span>
                                </button>
                                </div>
                            </div>
                            <!-- <div v-else>There is no image uploaded</div> -->
                            </div>
                        </div>
                        <div v-if="sliderImage != null" class="mb-4">
                            <p class="font-weight-bold" v-text="$t('riportalApp.company.upload.newLogo')">New logo:</p>
                            {{ sliderImage.name }}
                            <button type="button" class="btn btn-sm btn-danger ml-3" v-on:click="removeSliderImage()">
                            <span v-text="$t('entity.action.remove')">Save</span>
                            </button>
                        </div>
                    </div>
                </div>

                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.cmsSlider.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>

        <b-modal ref="deleteSliderImageModal" id="deleteSliderImageModal">
            <span slot="modal-title" v-text="'Potvrda brisanja slike slajdera'"></span>
            <div class="modal-body">
            <p v-text="'Da li ste sigurni da želite da izbrišete sliku slajdera?'"></p>
            </div>
            <div slot="modal-footer">
            <button type="button" class="btn btn-secondary" @click="closeDeleteSliderImageModal()" v-text="$t('entity.action.cancel')">
                Odustani
            </button>
            <button type="button" class="btn btn-danger" @click="deleteSliderImage()" v-text="$t('entity.action.delete')">Obriši</button>
            </div>
        </b-modal>
    </div>
</template>
<script lang="ts" src="./cms-slider-update.component.ts">
</script>
