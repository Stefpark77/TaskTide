<template>
    <div class="w-full max-w-xs mx-auto">
        <form class="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4">
          <h1 class="block text-gray-700 text-sm font-bold mb-2">Edit Event</h1>
          <div class="mb-4">
            <label class="block text-gray-700 text-sm font-bold mb-2" for="name">
              Name
            </label>
            <input
                class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                id="name" type="text" placeholder="Name" v-model="updateName"
            />
          </div>
          <div class="mb-6">
            <label class="block text-gray-700 text-sm font-bold mb-2" for="description">
              Description
            </label>
            <input
                class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                id="description" type="text" placeholder="Description" v-model="updateDescription"
            />
          </div>
          <div class="mb-6">
            <label class="block text-gray-700 text-sm font-bold mb-2" for="date">
              Date
            </label>
            <input
                class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                id="date" type="date" placeholder="Date" v-model="updateDate"
            />
          </div>
          <div class="flex items-center justify-between">
            <button
                class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
                type="button" @click="updateEvent"
            >
              Update Event
            </button>
          </div>
        </form>
    </div>
</template>

<script>
export default {
    data() {
        return {
          updateName: this.$store?.state?.updateName ?? '',
          updateDescription: this.$store?.state?.updateDescription ?? '',
          updateDate: this.$store?.state?.updateDate ?? '',
        };
    },
    watch: {
        updateName: {
            handler: function (val) {
                this.$store?.commit('setUpdateName', val);
            },
            deep: true,
        },
        updateDescription: {
            handler: function (val) {
                this.$store?.commit('setUpdateDescription', val);
            },
            deep: true,
        },
        updateDate: {
          handler: function (val) {
            this.$store?.commit('setUpdateDate', val);
          },
          deep: true,
        },
    },
    methods: {
      updateEvent() {
            this.$store?.commit('updateEvent', { name: this.updateName, description: this.updateDescription,  date: this.updateDate });
        },
    },
};
</script>