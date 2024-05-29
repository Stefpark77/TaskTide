<template>
  <div v-if="this.$store?.state?.showAddEvent">
    <div class="zoneAddEvent">
      <div class="w-full max-w-xs mx-auto">
        <div class="bg-white shadow-md rounded-3xl px-8 pt-6 pb-8 mb-4">
          <div class="mb-4 mt-4">
            <input
                class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                id="name" type="text" placeholder="Name" v-model="addName"/>
          </div>
          <div class="mb-6">
            <v-textarea
                class="resize-y overflow-auto shadow appearance-none border rounded w-full py-3 px-4 text-lg text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                id="description" type="text" placeholder="Description" v-model="addDescription" :disabled="addName==='disabled'" />
          </div>
          <div class="mb-6">
            <label class="block text-gray-700 text-sm font-bold mb-2" for="date">
              Start Date:
            </label>
            <input
                class="mb-6 shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                id="date" type="date" placeholder="Date" v-model="addDate"/>
            <input class="mb-6 shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                   type="time" id="dateTime" placeholder="dateTime" v-model="addDateTime" />
          </div>
          <div class="mb-6">
            <label class="block text-gray-700 text-sm font-bold mb-2" for="date">
              End Date:
            </label>
            <input
                class="mb-6 shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                id="date" type="date" placeholder="Date" v-model="addEndDate"/>
            <input class="mb-6 shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                   type="time" id="endDateTime" placeholder="endDateTime"  v-model="addEndDateTime"/>
          </div>
          <div class="flex items-end">

            <v-col cols="auto">
              <v-btn icon="mdi-plus-circle-multiple-outline" size="x-large"></v-btn>
            </v-col>
            <button
                class="left-0 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-2xl focus:outline-none focus:shadow-outline"
                type="button" @click="addEvent">
              Add Event
            </button>
            <button
                class="left-0 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-2xl focus:outline-none focus:shadow-outline"
                type="button" @click="cancelAdd">
              Cancel
            </button>
          </div>
        </div>
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
      addDateTime: this.$store?.state?.addDateTime ?? '',
      addEndDate: this.$store?.state?.addEndDate ?? '',
      addEndDateTime: this.$store?.state?.addEndDateTime ?? '',
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
    addDateTime: {
      handler: function (val) {
        this.$store?.commit('setAddDateTime', val);
      },
      deep: true,
    },
    addEndDate: {
      handler: function (val) {
        this.$store?.commit('setAddEndDate', val);
      },
      deep: true,
    },
    addEndDateTime: {
      handler: function (val) {
        this.$store?.commit('setAddEndDateTime', val);
      },
      deep: true,
    },
  },
  methods: {
    addEvent() {
      this.$store?.commit('createEvent', {name: this.addName, description: this.addDescription, date: this.addDate, endDate: this.addEndDate, startTime: this.addDateTime, endTime: this.addEndDateTime});
      this.$store?.commit('setShowAddEvent', false);
      this.$store?.commit('setShowEvents', true);
    },
    cancelAdd() {
      this.$store?.commit('setShowAddEvent', false);
      this.$store?.commit('setShowEvents', true);
    },

  },
};
</script>

<style>

.zoneAddEvent {
  width: 85%;
  height: 94%;
  bottom: 0;
  right: 0;
  position: absolute;
  background-color: lightsteelblue;
  display: flex;
  justify-content: center;
  align-items: center;
  color: black;
}

</style>