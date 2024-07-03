<template>
    <div class="form-group">
        <label class="form-control-label position-relative" v-text="textLabel"></label>
        <b-form-file
        style="margin-bottom: 5px;"
        v-model="formFiles"
        @input="appendFiles()"
        :state="Boolean(formFiles)"
        :placeholder="placeholdertext"
        multiple
        drop-placeholder="Drop file here..."
        :browse-text="browseButtonText"
        :accept="acceptType"
        :file-name-formatter="formatNames"
        :disabled="isUploadFilesDisabled"
        >
        </b-form-file>
        <div>
        <p
            v-if="isUploadFilesDisabled"
            class="text-danger small mb-2"
            v-text="textFileLimit"
        ></p>
        <p
            v-else
            class="small mb-0 text-info"
            v-text="textFileNumber + availableNumberOfFilesToAdd"
        ></p>
        <p class="small mb-0 text-info" v-text="textFileSize"></p>
        <!-- <p class="small mb-0 text-info">* Dozvoljene dimenzije slike su 50 x 50.</p> -->
        </div>
        <ol v-if="includedFilesFromParent.length > 0" class="p-0">
        <p class="font-weight-bold mt-3" v-text="textCurrentFiles">Current files:</p>
        <div v-for="file in includedFilesFromParent">
            <li class="ml-4 mb-3 pl-2">
            <img v-if="fileType === documentTypeOptions.IMAGE" :src="retrieveFile(file.filename)" width="50" />
            <a
                v-else-if="fileType === documentTypeOptions.DOCUMENT"
                class="text-info"
                :href="retrieveFile(file.filename)"
                target="_blank"
                title="Preuzmite dokument"
                >{{ file.filename }}
            </a>
            <button
                type="button"
                class="btn btn-sm btn-danger ml-3"
                @click="prepareDeleteFileModal(file.id)"
                v-b-modal.deleteFileModal
            >
                <span v-text="$t('entity.action.delete')">Save</span>
            </button>
            </li>
        </div>
        </ol>
        <ol class="p-0">
        <p v-if="files.length > 0" class="font-weight-bold mt-3" v-text="textNewFile">
            New files:
        </p>
        <li v-for="file in files" class="ml-4 mb-3">
            {{ file.name }}
            <button type="button" class="btn btn-sm btn-danger ml-3" v-on:click="removeFile(file.name)">
            <span v-text="$t('entity.action.remove')">Save</span>
            </button>
        </li>
        </ol>

        <b-modal :ref="deleteFileModalRef" :id="deleteFileModalRef">
            <span slot="modal-title" v-text="textDeleteFileTitle"></span>
            <div class="modal-body">
                <p v-text="textDeleteFileQuestion"></p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" @click="closeDeleteFileModal()" v-text="$t('entity.action.cancel')">
                Odustani
                </button>
                <button type="button" class="btn btn-danger" @click="deleteFile()" v-text="$t('entity.action.delete')">Obriši</button>
            </div>
        </b-modal>

    </div>
</template>

<script lang="ts" src="./file-upload.component.ts"></script>