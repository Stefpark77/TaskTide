<template>
  <div class="w-full max-w-xs mx-auto">
    <div class="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4">
      <h1 class="block text-gray-700 text-sm font-bold mb-2">Sign Up</h1>
      <div class="mb-4">
        <label class="block text-gray-700 text-sm font-bold mb-2" for="firstName">
          First Name:
        </label>
        <input
            class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            id="firstName" type="text" placeholder="First Name" v-model="addFirstName"
        />
      </div>
      <div class="mb-6">
        <label class="block text-gray-700 text-sm font-bold mb-2" for="lastName">
          Last Name:
        </label>
        <input
            class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            id="lastName" type="text" placeholder="last Name" v-model="addLastName"
        />
      </div>
      <div class="mb-6">
        <label class="block text-gray-700 text-sm font-bold mb-2" for="username">
          Username:
        </label>
        <input
            class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            id="username" type="text" placeholder="Username" v-model="addUsername"
        />
      </div>
      <div class="mb-6">
        <label class="block text-gray-700 text-sm font-bold mb-2" for="password">
          Password:
        </label>
        <input
            class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            id="password" type="password" placeholder="******************" v-model="addPassword"
        />
      </div>
      <div class="mb-6">
        <label class="block text-gray-700 text-sm font-bold mb-2" for="email">
          Email:
        </label>
        <input
            class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            id="email" type="text" placeholder="Email@yahoo.com" v-model="addEmail"
        />
      </div>

      <div class="flex items-center justify-between">
        <button
            class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
            type="button" @click="signUp"
        >
          Sign Up
        </button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      addFirstName: this.$store?.state?.addFirstName ?? '',
      addLastName: this.$store?.state?.addLastName ?? '',
      addUsername: this.$store?.state?.addUsername ?? '',
      addPassword: this.$store?.state?.addPassword ?? '',
      addEmail: this.$store?.state?.addEmail ?? '',
    };
  },
  watch: {
    addFirstName: {
      handler: function (val) {
        this.$store?.commit('setAddFirstNam', val);
      },
      deep: true,
    },
    addLastName: {
      handler: function (val) {
        this.$store?.commit('setAddLastName', val);
      },
      deep: true,
    },
    addUsername: {
      handler: function (val) {
        this.$store?.commit('setAddUsername', val);
      },
      deep: true,
    },
    addPassword: {
      handler: function (val) {
        this.$store?.commit('setAddPassword', val);
      },
      deep: true,
    },
    addEmail: {
      handler: function (val) {
        this.$store?.commit('setAddEmail', val);
      },
      deep: true,
    },
  },
  methods: {
    signUp() {
      this.$store?.commit('signUp', { firstName: this.addFirstName, lastName: this.addLastName, username: this.addUsername, password: this.addPassword, email: this.addEmail });
      this.$store?.commit('setShowSignUp', true);
    },
  },
};
</script>