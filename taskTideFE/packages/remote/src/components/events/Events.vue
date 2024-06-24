<template>
  <div v-if="this.$store?.state?.showEvents">
    <div class="zone">
      <div class="flex justify-start bg-white white-bar">
        <v-tabs>
          <v-text-field
              class="ml-5 border-r-2 border-l-2"
              width="300px"
              prepend-inner-icon="mdi-magnify"
              placeholder="Search By Name"
              v-model="searchBar"
          ></v-text-field>
        </v-tabs>
        <v-spacer></v-spacer>
        <v-tabs>
          <v-btn
              class="h-100 left-0 bg-blue-500 hover:bg-blue-700 text-white font-bold  focus:outline-none focus:shadow-outline"
              v-on:click="previousWeek">
            <v-icon icon="mdi-arrow-left-bold-circle-outline"/>
            PREVIOUS Week
          </v-btn>
          <v-tab
              prepend-icon="mdi-calendar"
              class="tab_week"
              readonly>
            {{ format(firstDayOfWeek, "MMMM dd, yyyy") }}
            <v-icon icon="mdi-arrow-right"/>
            <v-icon icon="mdi-calendar"/>
            {{ format(lastDayOfWeek, "MMMM dd, yyyy") }}
          </v-tab>

          <v-btn
              class="h-100 left-0 bg-blue-500 hover:bg-blue-700 text-white font-bold  focus:outline-none focus:shadow-outline"
              v-on:click="nextWeek">Next Week
            <v-icon icon="mdi-arrow-right-bold-circle-outline"/>
          </v-btn>
        </v-tabs>
        <v-spacer></v-spacer>
        <v-tabs>
          <v-tab
              prepend-icon="mdi-calendar-plus"
              class="menu_button"
              v-on:click="addEvent">
            New Event
          </v-tab>
        </v-tabs>
      </div>
      <div class="events">
        <div v-for="(dayEvents, index) in this.$store?.state?.eventsWeek">
          <v-card-title class="text-body-2 d-flex align-center mt-4"
                        v-if="dayEvents.filter(e => searchBar === '' || e.name.includes(searchBar))[0] !== undefined">
            <div class="text-h4">
              <a class="font-weight-bold  pb-3 border-b-2 mb-5  border-black" v-if="getDayOfWeek(index) > new Date &&
            format(getDayOfWeek(index), 'MMMM dd, yyyy') !== format(new Date, 'MMMM dd, yyyy')">

                <v-icon
                    color="#1e90ff"
                    icon="mdi-calendar-clock"
                    start
                    @click="test(dayEvents.filter(e => searchBar === '' || e.name.includes(searchBar)))"
                ></v-icon>
                {{ daysOfWeek[index] }} ( {{ format(getDayOfWeek(index), "MMMM dd, yyyy ") }})
              </a>
              <a class="font-weight-bold  pb-3 text-blue-500  border-b-2 mb-5  border-blue-500"
                 v-if="format(getDayOfWeek(index), 'MMMM dd, yyyy') === format(new Date, 'MMMM dd, yyyy')">
                <v-icon
                    color="#1e90ff"
                    icon="mdi-calendar-clock-outline"
                    start
                ></v-icon>
                Today, {{ daysOfWeek[index] }} ( {{ format(getDayOfWeek(index), "MMMM dd, yyyy ") }})
              </a>
              <a class="font-weight-bold pb-3 text-gray-500  border-b-2 mb-5  border-gray-500" v-if="getDayOfWeek(index) < new Date &&
            format(getDayOfWeek(index), 'MMMM dd, yyyy') !== format(new Date, 'MMMM dd, yyyy')">
                <v-icon
                    icon="mdi-calendar-multiple-check"
                    start
                ></v-icon>
                {{ daysOfWeek[index] }} ( {{ format(getDayOfWeek(index), "MMMM dd, yyyy ") }})
              </a>

            </div>
          </v-card-title>
          <v-card
              class=" my-8  event"
              v-for="event in dayEvents.filter(e => searchBar === '' || e.name.toLowerCase().includes(searchBar.toLowerCase()))"
              :key="event.id">
            <v-card-item>
              <v-card-title class="text-body-2 d-flex align-center ">
                <v-icon
                    color="#1e90ff"
                    icon="mdi-calendar"
                    start
                ></v-icon>
                <a v-if="isFullDay(event, getDayOfWeek(index))">Full Day</a>
                <div class="text-medium-emphasis font-weight-bold" v-if="!isFullDay(event, getDayOfWeek(index))">
                  {{ getStartHours(event, getDayOfWeek(index)) }}
                  <v-icon
                      color="#1e90ff"
                      icon="mdi-arrow-right-bold"
                      start
                  ></v-icon>
                  {{ getEndHours(event, getDayOfWeek(index)) }}
                </div>

                <v-spacer></v-spacer>

                <v-chip
                    class="ms-2 text-medium-emphasis"
                    prepend-icon="mdi-pencil"
                    color="#1e90ff"
                    size="small"
                    text="EDIT"
                    variant="outlined"
                    @click="updateEvent(event)"
                ></v-chip>

              </v-card-title>

              <div class="py-2">
                <div class="text-h6 border-b-2"><a class="font-bold">{{ event.name }}</a></div>

                <div class="font-weight-light text-medium-emphasis">
                  {{ event.description }}
                </div>
              </div>

            </v-card-item>

            <v-card-item>
              <v-spacer></v-spacer>
              <v-chip
                  color="#1e90ff"
                  size="small"
                  v-if="event.recurringTime!=='ONCE'"
                  :text="event.recurringTime"
                  variant="outlined"
              ></v-chip>
            </v-card-item>
          </v-card>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {format, parseISO} from "date-fns";

export default {
  computed: {
    firstDayOfWeek: function () {
      var curr = new Date;
      var day = curr.getDate() - curr.getDay() + 7 * this.numberOfWeeks + 1;
      return curr.setDate(day);
    },
    lastDayOfWeek: function () {
      var curr = new Date;
      var day = curr.getDate() - curr.getDay() + 7 * this.numberOfWeeks + 7;
      return curr.setDate(day);
    },
  },
  data() {
    return {
      events: this.$store?.state?.events ?? [],
      eventsWeek: this.$store?.state?.eventsWeek ?? [],
      searchBar: '',
      numberOfWeeks: 0,
      daysOfWeek: ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'],
    };
  },
  methods: {
    parseISO,
    format,
    addEvent() {
      this.$store?.commit('setShowAddEvent', true);
      this.$store?.commit('setShowEvents', false);
      this.numberOfWeeks = 0;
    },
    updateEvent(event) {
      this.$store?.commit('setUpdateEvent', event);
      this.$store?.commit('setShowEditEvent', true);
      this.$store?.commit('setShowEvents', false);
      this.numberOfWeeks = 0;
    },
    previousWeek() {
      this.numberOfWeeks--;
      this.$store?.commit('fetchWeekEvents', this.firstDayOfWeek);
    },
    nextWeek() {
      this.numberOfWeeks++;
      this.$store?.commit('fetchWeekEvents', this.firstDayOfWeek);
    },
    getDayOfWeek(numberOfDays) {
      var date = new Date(this.firstDayOfWeek);
      date.setDate(date.getDate() + numberOfDays);
      return date;
    },
    isFullDay(event, dayOfEvent) {
      if (format(parseISO(event.date), "HH:mm") === '00:00' &&
          format(parseISO(event.endDate), "HH:mm") === '23:59' &&
          format(parseISO(event.date), "MMMM dd, yyyy") === format(parseISO(event.endDate), "MMMM dd, yyyy")) {
        return true;
      }
      if (format(parseISO(event.date), "MMMM dd, yyyy") !== format(dayOfEvent, "MMMM dd, yyyy") &&
          format(parseISO(event.endDate), "MMMM dd, yyyy") !== format(dayOfEvent, "MMMM dd, yyyy") &&
          event.recurringTime === 'ONCE' &&
          dayOfEvent > parseISO(event.date) &&
          dayOfEvent < parseISO(event.endDate)) {
        return true;
      }
      return false;
    },
    getStartHours(event, dayOfEvent) {
      if (event.recurringTime !== 'ONCE') {
        return format(parseISO(event.date), "HH:mm");
      }
      if (format(parseISO(event.date), "MMMM dd, yyyy") !== format(dayOfEvent, "MMMM dd, yyyy") &&
          format(parseISO(event.endDate), "MMMM dd, yyyy") === format(dayOfEvent, "MMMM dd, yyyy")) {
        return '00:00';
      }
      return format(parseISO(event.date), "HH:mm");
    },
    getEndHours(event, dayOfEvent) {
      if (event.recurringTime !== 'ONCE') {
        return format(parseISO(event.endDate), "HH:mm");
      }
      if (format(parseISO(event.date), "MMMM dd, yyyy") === format(dayOfEvent, "MMMM dd, yyyy") &&
          format(parseISO(event.endDate), "MMMM dd, yyyy") !== format(dayOfEvent, "MMMM dd, yyyy")) {
        return '23:59';
      }
      return format(parseISO(event.date), "HH:mm");
    },
  },
};
</script>

<style>
.zone {
  width: 85%;
  height: 94%;
  bottom: 0;
  right: 0;
  position: absolute;
  background-color: lightsteelblue;
}

.events {
  width: 100%;
  align-items: center;
  display: flex;
  flex-direction: column;
  height: 96%;
  bottom: 0;
  right: 0;
  position: absolute;
  background-color: lightsteelblue;
  overflow: auto; /* Add scrollbar when needed */
}

.event {
  width: 1000px;
  height: fit-content; /* Adjust the height of your card */
  margin: 50px; /* Adjust the margin between cards */
  border: 1px solid #ccc;
  border-radius: 25px;
  box-shadow: -17px 17px 7px 0px rgba(0, 0, 0, 0.13), 0px 1px 2px 0px rgba(0, 0, 0, 0.11);
  background-color: white;
  display: flex;
  flex-direction: column;
  align-content: stretch;
  justify-content: space-between;
}

.tab_week {
  background-color: white;
  padding: 10px 50px;
  font-weight: bold;
  border-radius: 35px;
  justify-content: center;
  font-size: large;
  color: black;
  margin-up: 10%;
  margin-bottom: 50%;
}

.white-bar {
  height: 4%;
}
</style>