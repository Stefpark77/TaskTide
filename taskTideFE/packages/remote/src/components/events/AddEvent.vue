<template>
  <div class="w-full max-w-xs mx-auto">
    <div class="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4">
      <h1 class="block text-gray-700 text-sm font-bold mb-2">Add Event</h1>
      <div class="mb-4">
        <label class="block text-gray-700 text-sm font-bold mb-2" for="name">
          Name
        </label>
        <input
            class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            id="name" type="text" placeholder="Name" v-model="addName"
        />
      </div>
      <div class="mb-6">
        <label class="block text-gray-700 text-sm font-bold mb-2" for="description">
          Description
        </label>
        <input
            class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            id="description" type="text" placeholder="Description" v-model="addDescription"
        />
      </div>
      <div class="mb-6">
        <label class="block text-gray-700 text-sm font-bold mb-2" for="date">
          Date
        </label>
        <input
            class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            id="date" type="date" placeholder="Date" v-model="addDate"
        />
      </div>
      <div class="flex items-center justify-between">
        <button
            class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
            type="button" @click="addEvent"
        >
          Add Event
        </button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      addName: this.$store?.state?.addName ?? '',
      addDescription: this.$store?.state?.addDescription ?? '',
      addDate: this.$store?.state?.addDate ?? '',
    };
  },
  watch: {
    addName: {
      handler: function (val) {
        this.$store?.commit('setAddName', val);
      },
      deep: true,
    },
    addDescription: {
      handler: function (val) {
        this.$store?.commit('setAddDescription', val);
      },
      deep: true,
    },
    addDate: {
      handler: function (val) {
        this.$store?.commit('setAddDate', val);
      },
      deep: true,
    },
  },
  methods: {
    addEvent() {
      this.$store?.commit('createEvent', { name: this.addName, description: this.addDescription, date: this.addDate });
      this.$store?.commit('setShowAddEvent', false);
    },
  },
};
</script>