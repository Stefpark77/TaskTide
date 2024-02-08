<template>
  <div class="flex justify-center items-center h-screen">
    <div class="w-96">
      <div class="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4">
        <div class="mb-4">
          <label class="block text-gray-700 text-sm font-bold mb-2" for="username">
            Username:
          </label>
          <input
              class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
              id="username" type="text" placeholder="Username" v-model="username"
          />
        </div>
        <div class="mb-6">
          <label class="block text-gray-700 text-sm font-bold mb-2" for="password">
            Password:
          </label>
          <input
              class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
              id="password" type="password" placeholder="******************" v-model="password"
          />
        </div>
        <div class="flex items-center justify-between">
          <button
              class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
              type="button" @click="login"
          >
            Log In
          </button>
        </div>
        <div class="text-red-500 text-xs italic mt-2" v-if="this.$store?.state?.loginError">
          Invalid username or password
        </div>
      </div>
      <div v-if="!this.$store?.state?.showSignUp">
        <div class="flex justify-end">
          <button
              class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
              type="button"
              v-on:click="signUp"
          >
            New account?
          </button>
        </div>
        </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      username: this.$store?.state?.username ?? '',
      password: this.$store?.state?.password ?? '',
    };
  },
  watch: {
    username: {
      handler: function (val) {
        this.$store?.commit('setUsername', val);
      },
      deep: true,
    },
    password: {
      handler: function (val) {
        this.$store?.commit('setPassword', val);
      },
      deep: true,
    },
  },
  methods: {
    login() {
      this.$store?.commit('login', true);
    },
    signUp() {
      this.$store?.commit('setShowSignUp', true);
    },
  },

};
</script>