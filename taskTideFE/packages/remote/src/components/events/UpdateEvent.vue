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
                :rules="[value => value !== '' || 'This field is required.']"
                v-model="updateName"
            ></v-text-field>
            <v-textarea id="description" rows="10" min-width="500px" type="text" placeholder="Description"
                        v-model="updateDescription"
                        :rules="[value => value !== '' || 'This field is required.']"/>

          </div>
          <div class="mr-6">
            <v-text-field type="datetime-local"
                          label="Start Date"
                          :rules="[value => value !== '' || 'This field is required.']"
                          v-model="updateDate"
                          v-if="!fullDay"
            ></v-text-field>

            <v-text-field type="date"
                          label="Start Date"
                          :rules="[value => value !== '' || 'This field is required.']"
                          v-model="updateDate"
                          v-if="fullDay"
            ></v-text-field>

            <v-text-field type="datetime-local"
                          label="End Date"
                          :rules="[value => value !== '' || 'This field is required.',
                          value => parseISO(value) > parseISO(updateDate) || 'The end date must be after start date!']"
                          v-model="updateEndDate"
                          v-if="!fullDay && recurring === 'ONCE'"
                          :disabled="updateDate === ''"
            ></v-text-field>


            <v-text-field type="time"
                          label="End Date"
                          ref="endDateTime"
                          :rules="[value => value !== '' || 'This field is required.',
                          value =>  (parseInt(value.split(':')[0]) > parseISO(updateDate).getHours() ||
                          parseInt(value.split(':')[0]) === parseISO(updateDate).getHours() && parseInt(value.split(':')[1]) > parseISO(updateDate).getMinutes() ) ||
                          'The end date must be after start date!']"
                          v-model="updateEndDateTime"
                          v-if="!fullDay && recurring !== 'ONCE'"
                          :disabled="updateDate === ''"
            ></v-text-field>


            <v-select
                class="mb-5"
                :items="recurringValues"
                density="compact"
                label="Recurring"
                :rules="[value => value !== '' || 'This field is required.']"
                v-model="recurring"
            ></v-select>

            <v-checkbox label="All Day" v-model="fullDay" @click="resetDate"></v-checkbox>

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
                  :disabled="updateName === '' ||
             updateDescription === '' ||
             recurring === '' ||
             updateDate === undefined ||
             updateDate === null ||
             updateDate === '' ||
             !fullDay && recurring === 'ONCE' && (updateEndDate === '' ||  parseISO(updateEndDate) < parseISO(updateDate)) ||
             !fullDay && recurring !== 'ONCE' && (updateEndDateTime === '' || (parseInt(updateEndDateTime.split(':')[0]) < parseISO(updateDate).getHours() || parseInt(updateEndDateTime.split(':')[0]) === parseISO(updateDate).getHours() && parseInt(updateEndDateTime.split(':')[1]) <= parseISO(updateDate).getMinutes()))
              "
                  class="left-0 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-2xl focus:outline-none focus:shadow-outline disabledFunction"
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
import {format, parseISO} from "date-fns";

export default {
  data() {
    return {
      editEventId: this.$store?.state?.editEventId ?? '',
      updateName: this.$store?.state?.updateName ?? '',
      updateDescription: this.$store?.state?.updateDescription ?? '',
      updateDate: this.$store?.state?.updateDate ?? '',
      updateEndDate: this.$store?.state?.updateEndDate ?? '',
      updateEndDateTime: this.$store?.state?.updateEndDateTime ?? '',
      fullDay: parseISO(this.$store?.state?.updateDate).getDate() === parseISO(this.$store?.state?.updateEndDate).getDate()
          && parseISO(this.$store?.state?.updateDate).getHours() === 0 && parseISO(this.$store?.state?.updateDate).getMinutes() === 0
          && parseISO(this.$store?.state?.updateEndDate).getHours() === 23 && parseISO(this.$store?.state?.updateEndDate).getMinutes() === 59,
      recurring: this.$store?.state?.updateRecurring ?? 'ONCE',
      recurringValues: ['ONCE', 'DAILY', 'WEEKLY', 'BIWEEKLY', 'MONTHLY', 'ANNUAL'],
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
    parseISO,
    updateEvent() {
      if (this.recurring !== 'ONCE' && !this.fullDay) {
        let endDate = parseISO(this.updateDate);
        endDate.setHours(parseInt(this.updateEndDateTime.split(':')[0]));
        endDate.setMinutes(parseInt(this.updateEndDateTime.split(':')[1]));
        this.updateEndDate = format(endDate, "yyyy-MM-dd'T'HH:mm");
      }
      this.$store?.commit('updateEvent', {
        name: this.updateName,
        description: this.updateDescription,
        date: this.updateDate,
        endDate: this.updateEndDate,
        recurringTime: this.recurring,
        fullDay: this.fullDay
      });
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
    resetDate() {
      this.updateDate = '';
    }
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

.disabledFunction:disabled {
  background-color: dodgerblue;
  opacity: 75%;
}
</style>