<template>
  <div v-if="this.$store?.state?.showEvents">
    <div class="zone">
      <div class="flex justify-start bg-white">
        <v-spacer class="z-10"></v-spacer>
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
        <v-card
            class=" my-8  event" v-for="event in this.$store?.state?.events" :key="event.id">
          <v-card-item>
            <v-card-title class="text-body-2 d-flex align-center ">
              <v-icon
                  color="#1e90ff"
                  icon="mdi-calendar"
                  start
              ></v-icon>

              <span class="text-medium-emphasis font-weight-bold">
                {{ format(parseISO(event.date), "EEE MMMM dd, yyyy  |  HH:mm   ") }}
                <v-icon
                    color="#1e90ff"
                    icon="mdi-arrow-right-bold"
                    start
                ></v-icon>

              <v-icon
                  color="#1e90ff"
                  icon="mdi-calendar"
                  start
              ></v-icon>
                {{ format(parseISO(event.endDate), "EEE MMMM dd, yyyy  |  HH:mm   ") }}
              </span>

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
</template>

<script>
import {format, parseISO} from "date-fns";

export default {
  data() {
    return {
      events: this.$store?.state?.events ?? [],
    };
  },
  methods: {
    parseISO,
    format,
    addEvent() {
      this.$store?.commit('setShowAddEvent', true);
      this.$store?.commit('setShowEvents', false);
    },
    updateEvent(event) {
      this.$store?.commit('setUpdateEvent', event);
      this.$store?.commit('setShowEditEvent', true);
      this.$store?.commit('setShowEvents', false);
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

.dates {
  display: flex;
  flex-direction: row;
  justify-content: space-evenly;
}

.date_text {
  font-size: large;
  font-weight: bold;
}

.name_and_button {
  display: flex;
  justify-content: space-between;
  margin-bottom: 1%;
  font-size: x-large;
}

.description_container {
  display: flex;
  justify-content: space-evenly;
  margin-bottom: 1%;
}

.description {
  font-size: large;
  display: flex;
  justify-content: space-evenly;
  border: 1px solid #ccc;
  border-radius: 25px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  width: 85%;
  height: 94%;
  padding: 20px;

  margin-bottom: 1%;
}

.events {
  width: 85%;
  height: 96%;
  bottom: 0;
  right: 0;
  position: absolute;
  background-color: lightsteelblue;
  overflow: auto; /* Add scrollbar when needed */
}

.event {
  width: 80%; /* Adjust the width of your card */
  height: fit-content; /* Adjust the height of your card */
  margin: 50px; /* Adjust the margin between cards */
  border: 1px solid #ccc;
  border-radius: 25px;

  box-shadow: -17px 17px 7px 0px rgba(0, 0, 0, 0.13), 0px 1px 2px 0px rgba(0, 0, 0, 0.11);
  padding: 20px;
  background-color: white;
  display: flex;
  flex-direction: column;
  align-content: stretch;
  justify-content: space-between;
}

</style>