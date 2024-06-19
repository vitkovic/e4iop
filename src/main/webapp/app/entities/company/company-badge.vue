<template>
  <div>
    <div class="badge-container">
      <div class="badge-circle">
        <div class="badge-content">
          <span v-if="rating">{{ rating }}</span>
          <font-awesome-icon :icon="icon" class="fa-lg icon" :style="{ marginBottom: rating ? '5px' : '0' }"></font-awesome-icon>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./company-badge.component.ts"></script>

<style scoped>
.badge-container {
  display: inline-block;
  margin-top: 10px;
  margin-bottom: 10px;
}

.badge-circle {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  border: 2px solid #23384B;
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
}

.badge-content {
  text-align: center;
}

.badge-content span {
  display: block;
  font-size: 16px;
}

.badge-content .icon {
  font-size: 20px;
  
}
</style>
