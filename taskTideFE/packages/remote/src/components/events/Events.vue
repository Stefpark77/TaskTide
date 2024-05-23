<template>
  <div v-if="this.$store?.state?.showEvents">
    <div class="zone">
      <div class="flex justify-end">
        <button
            class="mt-5 mr-10 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-2xl border-gray-50 border-2 focus:outline-none focus:shadow-outline"
            type="button"
            v-on:click="addEvent">
          Add New Event?
        </button>
      </div>
      <div class="events">
        <div class="event" v-for="event in this.$store?.state?.events" :key="event.id">
          <div class="name_and_button">
            <a class="font-bold ">{{ event.name }}</a>
            <button
                class="bg-white hover:bg-blue-700 text-black  hover:text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
                type="button" @click="updateEvent(event)">
              ...
            </button>
          </div>
          <div class="description_container">
            <div class="description">
              {{event.description.length > 255 ? event.description.slice(0, 255) + '...' : event.description }}
            </div>
          </div>
          <div class="dates">
            <a class="date_text">{{ format(parseISO(event.date),"MMMM dd, yyyy  |  HH:mm")}}</a>
            <a>to</a>
<!--            <font-awesome-icon :icon="['fas', 'arrow-right']" />-->
            <a class="date_text">{{ format(parseISO(event.endDate),"MMMM dd, yyyy  |  HH:mm") }}</a>
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
.dates{
  display: flex;
  flex-direction: row;
  justify-content: space-evenly;
}
.date_text{
  font-size: large;
  font-weight: bold;
}
.name_and_button{
  display: flex;
  justify-content: space-between;
  margin-bottom: 1%;
  font-size: x-large;
}
.description_container{
  display: flex;
  justify-content: space-evenly;
  margin-bottom: 1%;
}
.description{
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
  height: 94%;
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
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  padding: 20px;
  background-color: white;
  display: flex;
  flex-direction: column;
  align-content: stretch;
  justify-content: space-between;
}

</style>