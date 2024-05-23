<template>
  <div v-if="this.$store?.state?.showEditEvent">
    <div class="zoneAddEvent">
      <div class="w-full max-w-xs mx-auto">
        <div class="bg-white shadow-md rounded-3xl px-8 pt-6 pb-8 mb-4">
          <div class="mb-4 mt-4">
            <input
                class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                id="name" type="text" placeholder="Name" v-model="updateName"/>
          </div>
          <div class="mb-6">
            <input
                class="resize-y overflow-auto shadow appearance-none border rounded w-full py-3 px-4 text-lg text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                id="description" type="text" placeholder="Description" v-model="updateDescription"/>
          </div>
          <div class="mb-6">
            <label class="block text-gray-700 text-sm font-bold mb-2" for="date">
              Start Date:
            </label>
            <input
                class="mb-6 shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                id="date" type="date" placeholder="Date" v-model="updateDate"/>
            <input class="mb-6 shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                   type="time" id="dateTime" placeholder="dateTime" v-model="updateDateTime" />
          </div>
          <div class="mb-6">
            <label class="block text-gray-700 text-sm font-bold mb-2" for="date">
              End Date:
            </label>
            <input
                class="mb-6 shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                id="date" type="date" placeholder="Date" v-model="updateEndDate"/>
            <input class="mb-6 shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                   type="time" id="endDateTime" placeholder="endDateTime"  v-model="updateEndDateTime"/>
          </div>
          <div class="flex items-end">
            <button
                class="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded-2xl focus:outline-none focus:shadow-outline"
                type="button" @click="removeEvent(editEventId)">
              Delete
            </button>
            <button
                class="left-0 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-2xl focus:outline-none focus:shadow-outline"
                type="button" @click="updateEvent">
              Update
            </button>
            <button
                class="left-0 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-2xl focus:outline-none focus:shadow-outline"
                type="button" @click="cancelUpdate">
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
          editEventId: this.$store?.state?.editEventId ?? '',
          updateName: this.$store?.state?.updateName ?? '',
          updateDescription: this.$store?.state?.updateDescription ?? '',
          updateDate: this.$store?.state?.updateDate ?? '',
          updateDateTime: this.$store?.state?.updateDateTime ?? '',
          updateEndDate: this.$store?.state?.updateEndDate ?? '',
          updateEndDateTime: this.$store?.state?.updateEndDateTime ?? '',
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
      updateDateTime: {
        handler: function (val) {
          this.$store?.commit('setUpdateDateTime', val);
        },
        deep: true,
      },
      updateEndDate: {
        handler: function (val) {
          this.$store?.commit('setUpdateEndDate', val);
        },
        deep: true,
      },
      updateEndDateTime: {
        handler: function (val) {
          this.$store?.commit('setUpdateEndDateTime', val);
        },
        deep: true,
      },
    },
    methods: {
      updateEvent() {
            this.$store?.commit('updateEvent', { name: this.updateName, description: this.updateDescription,  date: this.updateDate,  endDate: this.updateEndDate, startTime: this.updateDateTime, endTime: this.updateEndDateTime});
            this.$store?.commit('setShowEditEvent', false);
            this.$store?.commit('setShowEvents', true);
        },
      cancelUpdate() {
        this.$store?.commit('setShowEditEvent', false);
        this.$store?.commit('setShowEvents', true);
      },
      removeEvent(id) {
        this.$store?.commit('removeEvent', id);
        this.$store?.commit('setShowEditEvent', false);
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