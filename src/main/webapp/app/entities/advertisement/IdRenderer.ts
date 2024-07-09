import { ICellRendererParams } from '@ag-grid-community/core';


export function IdRenderer(params: ICellRendererParams, tlang) {
    console.log(tlang)
 	//const lnktxt = this.$t('entity.action.view');
    const link = `<a href="/b2b/advertisement/${params.value}/view" target="_blank">Preview</a>`;
    
   
    return link;
}