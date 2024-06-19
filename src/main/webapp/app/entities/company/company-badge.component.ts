import { Component, Vue, Prop } from 'vue-property-decorator';

@Component
export default class RatingBadge extends Vue {
  @Prop({ default: null }) rating!: number | null;
  @Prop({ required: true }) icon!: string;
}
