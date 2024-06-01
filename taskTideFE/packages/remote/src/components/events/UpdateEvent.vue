<template>
  <div v-if="this.$store?.state?.showEditEvent">
    <div class="zoneAddEvent">
      <div class="w-full max-w-xs mx-auto flex justify-center">
        <div class="bg-white shadow-md rounded-3xl px-8 pt-6 pb-8 mb-4 flex beautifulShadow">
          <div class="mr-6">
            <v-text-field
                hide-details="auto"
                class="mb-5"
                label="Name"
                v-model="updateName"
            ></v-text-field>
            <v-textarea id="description" rows="10" min-width="500px" type="text" placeholder="Description" v-model="updateDescription" />

          </div>
          <div class="mr-6">
            <v-text-field type="datetime-local"
                          label = "Start Date"
                          v-model="updateDate"
            ></v-text-field>

            <v-text-field type="datetime-local"
                          label = "End Date"
                          v-model="updateEndDate"
                          :disabled="fullDay"
            ></v-text-field>

            <v-select
                class="mb-5"
                :items="recurringValues"
                density="compact"
                label="Recurring"
                v-model = "recurring"
            ></v-select>

            <v-checkbox label="All Day" v-model = "fullDay"></v-checkbox>

            <div class="flex items-end">

              <v-btn
                  class="mr-5"
                  type="button" @click="cancelUpdate">
                Cancel
              </v-btn>
              <v-btn
                  class="bg-red-200 mr-5 hover:bg-red-700 text-white font-bold py-2 px-4 rounded-2xl focus:outline-none focus:shadow-outline"
                  type="button" @click="removeEvent(editEventId)">
                Delete
              </v-btn>
              <v-btn
                  class="left-0 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-2xl focus:outline-none focus:shadow-outline"
                  type="button" @click="updateEvent">
                Update
              </v-btn>
            </div>
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
          updateEndDate: this.$store?.state?.updateEndDate ?? '',
          fullDay: false,
          recurring: this.$store?.state?.updateRecurring ?? 'ONCE',
          recurringValues: ['ONCE', 'WEEKLY', 'BIWEEKLY', 'MONTHLY', 'ANNUAL'],
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
      updateEndDate: {
        handler: function (val) {
          this.$store?.commit('setUpdateEndDate', val);
        },
        deep: true,
      },
    },
    methods: {
      updateEvent() {
            this.$store?.commit('updateEvent', { name: this.updateName, description: this.updateDescription,  date: this.updateDate,  endDate: this.updateEndDate, recurringTime: this.recurring, fullDay: this.fullDay});
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