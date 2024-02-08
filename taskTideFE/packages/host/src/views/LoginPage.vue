<template>
  <Login />
  <div v-if="showSignUp"><SignUp /></div>
  <router-view></router-view>
</template>

<script>
import { defineAsyncComponent } from 'vue';

export default {
  components: {
    Login: defineAsyncComponent(() => import("remote/Login")),
    SignUp: defineAsyncComponent(() => import("remote/SignUp")),
  },
  computed: {
    showSignUp() {
      return this.$store?.state?.showSignUp ?? false;
    },
  },
};
</script>