<template>
  <div class="form-group">
    <label class="form-control-label position-relative font-weight-bold" v-text="textLabel"></label>
    <b-form-file
      style="margin-bottom: 5px;"
      v-model="formFiles"
      class="customPlaceholder"
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
      <p v-if="isUploadFilesDisabled" class="text-danger small mb-2" v-text="textFileLimit"></p>
      <p v-else class="small mb-0 text-info" v-text="textFileNumber + availableNumberOfFilesToAdd"></p>
      <p class="small mb-0 text-info" v-text="textFileSize"></p>
      <p class="small mb-0 text-info" v-text="textFileDimensions"></p>
    </div>
    <div v-if="includedFilesFromParent.length > 0" class="p-0">
      <p class="font-weight-bold mt-3" v-text="textCurrentFiles">Current files:</p>
      <ol class="d-flex flex-wrap p-0">
        <div v-for="file in includedFilesFromParent">
          <div v-if="fileType === documentTypeOptions.IMAGE" class="image-item mb-4 mr-2">
            <div class="img-box">
              <img :src="retrieveFile(file.filename)" />
            </div>
            <button type="button" class="btn btn-sm btn-danger mt-3" @click="prepareDeleteFileModal(file.id)" v-b-modal.deleteFileModal>
              <span v-text="$t('entity.action.delete')">Save</span>
            </button>
          </div>
          <div v-else-if="fileType === documentTypeOptions.DOCUMENT" class="p-0">
            <li class="ml-4 mb-3 pl-2">
              <a class="text-info document-content" :href="retrieveFile(file.filename)" target="_blank" title="Preuzmite dokument"
                >{{ documentFileName(file.filename) }}
              </a>
              <button type="button" class="btn btn-sm btn-danger ml-3" @click="prepareDeleteFileModal(file.id)" v-b-modal.deleteFileModal>
                <span v-text="$t('entity.action.delete')">Save</span>
              </button>
            </li>
          </div>
        </div>
      </ol>
    </div>
    <ol class="p-0">
      <p v-if="files.length > 0" class="font-weight-bold mt-3" v-text="textNewFile">
        New files:
      </p>
      <li v-for="file in files" class="ml-4 mb-3 document-content">
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
        <button type="button" class="btn btn-success" @click="deleteFile()" v-text="$t('entity.action.delete')">Obriši</button>
        <button type="button" class="btn btn-danger" @click="closeDeleteFileModal()" v-text="$t('entity.action.cancel')">
          Odustani
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./file-upload.component.ts"></script>

<style scoped>
.customPlaceholder {
  overflow: hidden;
}

.image-item {
  width: 70px;
  margin-bottom: 10px;
  list-style-type: none;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.img-box {
  width: 50px;
  height: 50px;
}

.img-box img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
</style>
