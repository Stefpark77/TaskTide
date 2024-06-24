<template>
  <div v-if="this.$store?.state?.showAddEvent">
    <div class="zoneAddEvent">
      <div>
        <div class="bg-white shadow-md rounded-3xl px-8 pt-6 pb-8 mb-4 flex beautifulShadow">
          <div class="mr-6">
            <v-text-field
                hide-details="auto"
                class="mb-5"
                label="Name"
                :rules="[value => value !== '' || 'This field is required.']"
                v-model="addName"
            ></v-text-field>
            <v-textarea id="description" rows="10" min-width="500px" type="text" placeholder="Description"
                        v-model="addDescription"
                        :rules="[value => value !== '' || 'This field is required.']"/>
          </div>
          <div class="mr-6">
            <v-text-field type="datetime-local"
                          label="Start Date"
                          :rules="[value => value !== '' || 'This field is required.']"
                          v-model="addDate"
                          v-if="!fullDay"
            ></v-text-field>

            <v-text-field type="date"
                          label="Start Date"
                          :rules="[value => value !== '' || 'This field is required.']"
                          v-model="addDate"
                          v-if="fullDay"
            ></v-text-field>

            <v-text-field type="datetime-local"
                          label="End Date"
                          :rules="[value => value !== '' || 'This field is required.',
                          value => parseISO(value) > parseISO(addDate) || 'The end date must be after start date!']"
                          v-model="addEndDate"
                          v-if="!fullDay && recurring === 'ONCE'"
                          :disabled="addDate === ''"
            ></v-text-field>


            <v-text-field type="time"
                          label="End Date"
                          ref="endDateTime"
                          :rules="[value => value !== '' || 'This field is required.',
                          value =>  (parseInt(value.split(':')[0]) > parseISO(addDate).getHours() ||
                          parseInt(value.split(':')[0]) === parseISO(addDate).getHours() && parseInt(value.split(':')[1]) > parseISO(addDate).getMinutes() ) ||
                          'The end date must be after start date!']"
                          v-model="addEndDateTime"
                          v-if="!fullDay && recurring !== 'ONCE'"
                          :disabled="addDate === ''"
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
                  type="button" @click="cancelAdd">
                Cancel
              </v-btn>
              <v-btn
                  :disabled="addName === '' ||
             addDescription === '' ||
             recurring === '' ||
             addDate === undefined ||
             addDate === null ||
             addDate === '' ||
             !fullDay && recurring === 'ONCE' && (addEndDate === '' ||  parseISO(addEndDate) < parseISO(addDate)) ||
             !fullDay && recurring !== 'ONCE' && (addEndDateTime === '' || (parseInt(addEndDateTime.split(':')[0]) < parseISO(addDate).getHours() || parseInt(addEndDateTime.split(':')[0]) === parseISO(addDate).getHours() && parseInt(addEndDateTime.split(':')[1]) <= parseISO(addDate).getMinutes()))
              "
                  class="left-0 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-2xl focus:outline-none focus:shadow-outline disabledFunction"
                  type="button" @click="addEvent">
                Add Event
              </v-btn>
            </div>
          </div>

          <div class="mb-6">
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
      addName: '',
      addDescription: '',
      addDate: '',
      addEndDate: '',
      addEndDateTime: '',
      fullDay: false,
      recurring: 'ONCE',
      recurringValues: ['ONCE', 'DAILY', 'WEEKLY', 'BIWEEKLY', 'MONTHLY', 'ANNUAL'],
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
    addEndDate: {
      handler: function (val) {
        this.$store?.commit('setAddEndDate', val);
      },
      deep: true,
    },
  },
  methods: {
    parseISO,
    addEvent() {
      if (this.recurring !== 'ONCE' && !this.fullDay) {
        let endDate = parseISO(this.addDate);
        endDate.setHours(parseInt(this.addEndDateTime.split(':')[0]));
        endDate.setMinutes(parseInt(this.addEndDateTime.split(':')[1]));
        this.addEndDate = format(endDate, "yyyy-MM-dd'T'HH:mm");
      }
      this.$store?.commit('createEvent', {
        name: this.addName,
        description: this.addDescription,
        date: this.addDate,
        endDate: this.addEndDate,
        recurringTime: this.recurring,
        fullDay: this.fullDay
      });
      this.$store?.commit('setShowAddEvent', false);
      this.$store?.commit('setShowEvents', true);
    },
    cancelAdd() {
      this.$store?.commit('setShowAddEvent', false);
      this.$store?.commit('setShowEvents', true);
    },
    resetDate() {
      this.addDate = '';
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

.beautifulShadow {
  box-shadow: -17px 17px 7px 0px rgba(0, 0, 0, 0.13), 0px 1px 2px 0px rgba(0, 0, 0, 0.11);
}

.disabledFunction:disabled {
  background-color: dodgerblue;
  opacity: 75%;
}
</style>