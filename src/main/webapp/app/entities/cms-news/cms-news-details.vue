<template>
    <div class="row justify-content-center">
      <div class="col-8">
        <div v-if="cmsNews">
          <h2 class="jh-entity-heading"><span>{{ $t('riportalApp.cmsNews.detail.title') }}</span> {{ cmsNews.id }}</h2>
          <dl class="row jh-entity-details">
            <dt>
              <span>{{ $t('riportalApp.cmsNews.createdAt') }}</span>
            </dt>
            <dd>
              <span v-if="cmsNews.createdAt">{{ $d(Date.parse(cmsNews.createdAt), 'long') }}</span>
            </dd>
            <dt>
              <span>{{ $t('riportalApp.cmsNews.modifiedAt') }}</span>
            </dt>
            <dd>
              <span v-if="cmsNews.modifiedAt">{{ $d(Date.parse(cmsNews.modifiedAt), 'long') }}</span>
            </dd>
            <dt>
              <span>{{ $t('riportalApp.cmsNews.title') }}</span>
            </dt>
            <dd>
              <span>{{ cmsNews.title }}</span>
            </dd>
            <dt>
              <span>{{ $t('riportalApp.cmsNews.content') }}</span>
            </dt>
            <dd>
              <span>{{ cmsNews.content }}</span>
            </dd>
            <dt>
              <span>{{ $t('riportalApp.cmsNews.document') }}</span>
            </dt>
            <dd>
              <span v-for="(document, i) in cmsNews.documents" :key="document.id">{{ i > 0 ? ', ' : '' }}
                <router-link :to="{ name: 'DocumentView', params: { documentId: document.id }}">{{ document.id }}</router-link>
              </span>
            </dd>
            <dt>
              <span>{{ $t('riportalApp.cmsNews.type') }}</span>
            </dt>
            <dd>
              <div v-if="cmsNews.type">
                <router-link :to="{ name: 'CmsPageTypeView', params: { cmsPageTypeId: cmsNews.type.id }}">{{ cmsNews.type.id }}</router-link>
              </div>
            </dd>
            <dt>
              <span>{{ $t('riportalApp.cmsNews.createdBy') }}</span>
            </dt>
            <dd>
              <div v-if="cmsNews.createdBy">
                <router-link :to="{ name: 'PortalUserView', params: { portalUserId: cmsNews.createdBy.id }}">{{ cmsNews.createdBy.id }}</router-link>
              </div>
            </dd>
            <dt>
              <span>{{ $t('riportalApp.cmsNews.modifiedBy') }}</span>
            </dt>
            <dd>
              <div v-if="cmsNews.modifiedBy">
                <router-link :to="{ name: 'PortalUserView', params: { portalUserId: cmsNews.modifiedBy.id }}">{{ cmsNews.modifiedBy.id }}</router-link>
              </div>
            </dd>
          </dl>
          <button type="button" @click.prevent="previousState()" class="btn btn-info">
            <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span>{{ $t('entity.action.back') }}</span>
          </button>
          <router-link v-if="cmsNews.id" :to="{ name: 'CmsNewsEdit', params: { cmsNewsId: cmsNews.id }}" tag="button" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span>{{ $t('entity.action.edit') }}</span>
          </router-link>
        </div>
      </div>
    </div>
  </template>
  

<script lang="ts" src="./cms-news-details.component.ts">
</script>
