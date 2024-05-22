import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRiCalendar } from '@/shared/model/ri-calendar.model';
import RiCalendarService from './ri-calendar.service';

@Component
export default class RiCalendarDetails extends Vue {
  @Inject('riCalendarService') private riCalendarService: () => RiCalendarService;
  public riCalendar: IRiCalendar = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.riCalendarId) {
        vm.retrieveRiCalendar(to.params.riCalendarId);
      }
    });
  }

  public retrieveRiCalendar(riCalendarId) {
    this.riCalendarService()
      .find(riCalendarId)
      .then(res => {
        this.riCalendar = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
