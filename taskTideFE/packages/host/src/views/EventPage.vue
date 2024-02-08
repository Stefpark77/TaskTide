<template>
  <Header />
  <Events />
  <div class="mt-10"/>
  <div v-if="showAddEvent"><AddEvent /></div>
  <div v-if="showEditEvent"><UpdateEvent /></div>
  <router-view></router-view>
</template>

<script>
import { defineAsyncComponent } from 'vue';

export default {
  beforeCreate() {
    this.$store?.commit('fetchEvents', true);
  },
  mounted() {
    this.$store?.commit('fetchEvents', true);
  },
  components: {
    Events: defineAsyncComponent(() => import('remote/Events')),
    Header: defineAsyncComponent(() => import('remote/Header')),
    AddEvent: defineAsyncComponent(() => import('remote/AddEvent')),
    UpdateEvent: defineAsyncComponent(() => import('remote/UpdateEvent')),
  },
  computed: {
    showAddEvent() {
      return this.$store?.state?.showAddEvent ?? false;
    },
    showEditEvent() {
      return this.$store?.state?.showEditEvent ?? false;
    },
  },
};
</script>