import http from '../axioshttp';
import axios from 'axios';

class UploadFilesService {
  upload(file, onUploadProgress) {
    let formData = new FormData();

    formData.append('file', file);

    return axios.post('api/upload', formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
      onUploadProgress,
    });
  }

  getFiles() {
    return axios.get('api/files');
  }

  renameFile(originalFile, newName) {
    return new File([originalFile], newName, {
      type: originalFile.type,
      lastModified: originalFile.lastModified,
    });
  }

  getFile(link) {
    axios
      .get('api/files/' + link, { responseType: 'blob' })
      .then(response => {
        var href = URL.createObjectURL(new Blob([response.data], { type: 'application/pdf' }));
        const linko = document.createElement('a');
        linko.href = href;
        linko.setAttribute('download', 'file_' + link); //any extension...
        document.body.appendChild(linko);
        linko.click();
        document.body.removeChild(linko);
        URL.revokeObjectURL(href);
      })
      .catch(e => {
        console.log(e);
      });
  }
}

export default new UploadFilesService();
