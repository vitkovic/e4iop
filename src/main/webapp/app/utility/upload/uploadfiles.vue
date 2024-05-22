<template>
<div>
    <div v-if="currentFile" class="progress">
      <div
        class="progress-bar progress-bar-info progress-bar-striped"
        role="progressbar"
        :aria-valuenow="progress"
        aria-valuemin="0"
        aria-valuemax="100"
        :style="{ width: progress + '%' }"
      >
        {{ progress }}%
      </div>
    </div>
    
	<div>
		 <button class="btn btn-primary" id="slFile" ref='slFile' type="button" @click="choseUpload()">{{ txtslfile }}</button>
  			<input class="btn btn-primary" type="file" id="file_0" ref="file_0" @change="selectFile(0)" style="display:none">
  			<div class="alert alert-light" style="color:#0d6efd;" id="divMessage0" ref="divMessage0" role="alert">{{ message }} </div>
  			
  			<button class="btn btn-primary" type="button" :disabled="true" ref='upplbutt_0' @click="upload(0)">{{ txtupload }}</button>
  			<div class="alert alert-light" id="divMessage1" ref="divMessage1" role="alert">{{ messageupload }} </div>
  	</div>
  </div>
</template>

<script>

import UploadService from "./upload_service";

//const Allowedfiletypes = ['text/csv','application/msword','application/vnd.openxmlformats-officedocument.wordprocessingml.document','application/pdf','image/jpeg', 'image/png'];
const Allowedfiletypes = ['application/pdf'];
const numUploads = 2; 
function saveafter(obj) {
	
	obj.$refs.form_submit.click();
	
}
export default {
  name: "upload-files",
  props: ['text_field','rid','formn','pageObj','shortname'],
  data() {
    return {
	  selectedFiles: undefined,
      currentFile: undefined,
      progress: 0,
      message: this.$t('riportalApp.researchInfrastructure.upload.allowedtypes') + " / " + this.$t('riportalApp.researchInfrastructure.upload.namecontent'),
      messageupload: this.$t('riportalApp.researchInfrastructure.upload.notyetuploaded'),
      fileInfos: [],
      filetype:"",
      filenameout:"",
      txtslfile:this.$t('riportalApp.researchInfrastructure.upload.txtslfile'),
      txtupload:this.$t('riportalApp.researchInfrastructure.upload.txtupload'),
      filetypes:this.$t('riportalApp.researchInfrastructure.upload.allowedtypes'),
      sucss : false,
      uploadFiles:[],
      upfile:'',
      obj:null
     
    };
  },
  methods: {
	choseUpload() {
		//console.log('choseUpload')
		this.$refs['file_0'].click();
	},  
  	selectFile(i) {
	  this.progress = 0;
      this.uploadFiles = new Array();
      this.selectedFiles = new Array();
      const refName = 'file_' + i;
      this.upfile = this.$refs[refName].files[0];
      const filename = this.upfile.name;
      var fname = filename.substr(0, filename.lastIndexOf('.'));
      this.filenameout = filename;
	  if (/^[A-Za-z0-9]*$/.test(fname) != true) 
      {
        this.message =  this.$t('riportalApp.researchInfrastructure.upload.namecontent') + this.upfile.name;
        this.$refs['upplbutt_'+i].disabled = true;
        return;
      } 
      if (Allowedfiletypes.includes(this.upfile.type) != true) {
        this.message = this.$t('riportalApp.researchInfrastructure.upload.badtypeupload') + this.upfile.name;
      } else {
        this.selectedFiles = this.$refs[refName].files[0];
        this.$refs['upplbutt_'+i].disabled = false;
        const index = this.uploadFiles.indexOf(this.selectedFiles); // 👉️  0
        if (index !== -1) {
          this.uploadFiles[index] = this.selectedFiles;
        } else if (this.uploadFiles.length == numUploads ) {
          this.uploadFiles[i] = this.selectedFiles
        } else
          this.uploadFiles.push(this.selectedFiles)
          console.log(this.formn);
          	this.formn.$refs[this.text_field].focus();
	  		this.formn.$refs[this.text_field].value = this.filenameout;
	  		this.pageObj[this.text_field] = this.filenameout;
	  		this.message = this.$t('riportalApp.researchInfrastructure.upload.allowedtypes')
       		this.messageupload = this.$t('riportalApp.researchInfrastructure.upload.uploading') + this.upfile.name;
      }
    },
    upload(i) {
	   //console.log('upload');
	  this.progress = 0;
      this.currentFile = this.uploadFiles[i];
      const filename =  this.currentFile.name;
      var fname = filename.substr(0, filename.lastIndexOf('.'));
      var fext = filename.substr(filename.length-4);
      const newname = this.shortname + "_"+ this.pageObj.id + '_' + fname + fext ;
      const nfl = UploadService.renameFile(this.currentFile,newname);
      this.upfile = nfl;
      this.pageObj[this.text_field]=this.upfile.name;
      UploadService.upload(nfl, event => {
        this.progress = Math.round((100 * event.loaded) / event.total);
      })
        .then(response => {
		   this.messageupload = response.data.message;
	       this.messageupload = this.$t('riportalApp.researchInfrastructure.upload.uploaded') + this.upfile.name;
          // this.$notify({text:JSON.stringify(this.$t('riportalApp.researchInfrastructure.upload.uploaded') + " " + this.upfile.name).replace(/["]/g, ''), type:'info', duration:5000});   
           setTimeout(saveafter,0,this.formn);
         })
        .catch(() => {
		  this.progress = 0;
          this.messageupload = this.$t('riportalApp.researchInfrastructure.upload.badupload');
         // this.$notify({text:JSON.stringify(this.$t('riportalApp.researchInfrastructure.upload.badupload') + " " + this.upfile.name).replace(/["]/g, ''), type:'error', duration:5000});   
          this.currentFile = undefined;
        });
       // this.$refs['upplbutt_'+i].disabled = true;
    },
    created() {
		
  	},
    mounted() {
		
  	}},
  	
  };
</script>