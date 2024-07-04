import Component from 'vue-class-component';
import { Vue, Inject } from 'vue-property-decorator';

import { CmsPageTypeOptions } from '@/shared/model/cms-page-type.model';

@Component
export default class CMSB2BPages extends Vue {
  public cmsPageTypeOptions = CmsPageTypeOptions;
}
