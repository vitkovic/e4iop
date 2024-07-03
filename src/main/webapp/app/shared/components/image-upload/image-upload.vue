<template>
    <div class="form-group">
        <label class="form-control-label position-relative" v-text="$t('riportalApp.advertisement.upload.imgUpload')"></label>
        <b-form-file
        style="margin-bottom: 5px;"
        v-model="formImages"
        @input="appendImageFiles()"
        :state="Boolean(formImages)"
        :placeholder="placeholdertext"
        multiple
        drop-placeholder="Drop file here..."
        :browse-text="browseButtonText"
        accept=".jpg, .jpeg, .png, .svg"
        :file-name-formatter="formatNames"
        :disabled="isUploadImageFilesDisabled"
        >
        </b-form-file>
        <div>
        <p
            v-if="isUploadImageFilesDisabled"
            class="text-danger small mb-2"
            v-text="$t('riportalApp.advertisement.upload.imgInfo.imgLimit')"
        ></p>
        <p
            v-else
            class="small mb-0 text-info"
            v-text="`${$t('riportalApp.advertisement.upload.imgInfo.imgNumber')} ${availableNumberOfImagesToAdd}.`"
        ></p>
        <p class="small mb-0 text-info" v-text="$t('riportalApp.advertisement.upload.imgInfo.imgSize')"></p>
        <!-- <p class="small mb-0 text-info">* Dozvoljene dimenzije slike su 50 x 50.</p> -->
        </div>
        <ol class="p-0">
        <p class="font-weight-bold mt-3" v-text="$t('riportalApp.advertisement.upload.currentImg')">Current images:</p>
        <div v-for="image in includedImages">
            <li class="ml-4 mb-3 pl-2">
            <img :src="image.name" width="50" />
            <!-- <img :src="advertisementService().retrieveImage(image.name)" width="50" /> -->
            <!-- {{ document.filename }} -->
            <button
                type="button"
                class="btn btn-sm btn-danger ml-3"
                @click="openDeleteImageModal(image.id)"
                v-b-modal.deleteImageModal
            >
                <span v-text="$t('entity.action.delete')">Save</span>
            </button>
            </li>
        </div>
        </ol>
        <ol class="p-0">
        <p v-if="imageFiles.length > 0" class="font-weight-bold mt-3" v-text="$t('riportalApp.advertisement.upload.newImg')">
            New images:
        </p>
        <li v-for="image in imageFiles" class="ml-4 mb-3">
            {{ image.name }}
            <button type="button" class="btn btn-sm btn-danger ml-3" v-on:click="removeImage(image.name)">
            <span v-text="$t('entity.action.remove')">Save</span>
            </button>
        </li>
        </ol>
    </div>
</template>

<script lang="ts" src="./image-upload.component.ts"></script>