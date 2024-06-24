<template>
  <div v-if="this.$store?.state?.showAddProject">
    <div class="zoneAddProject">
      <div class="w-full max-w-xs mx-auto flex justify-center">
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

            <v-text-field type="date"
                          label="Deadline"
                          :rules="[value => value !== '' || 'This field is required.',
                        value => parseISO(value) > Date.now() || 'The deadline must be after today\'s date!']"
                          v-model="addDeadline"
            ></v-text-field>

            <div class="flex items-end justify-end">
              <v-btn
                  class="mr-5"
                  type="button" @click="cancelAdd">
                Cancel
              </v-btn>
              <v-btn
                  :disabled="addName === '' ||
             addDescription === '' ||
             addDeadline === '' || parseISO(addDeadline) <= Date.now()"
                  class="left-0 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-2xl focus:outline-none focus:shadow-outline disabledFunction"
                  type="button" @click="addProject">
                Add Project
              </v-btn>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {parseISO} from "date-fns";

export default {
  data() {
    return {
      addName: '',
      addDescription: '',
      addDeadline: '',
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
    addDeadline: {
      handler: function (val) {
        this.$store?.commit('setAddDeadline', val);
      },
      deep: true,
    },
  },
  methods: {
    parseISO,
    addProject() {
      this.$store?.commit('createProject', {
        name: this.addName,
        description: this.addDescription,
        deadline: this.addDeadline
      });
      this.$store?.commit('setShowAddProject', false);
      this.$store?.commit('setShowProjects', true);
    },
    cancelAdd() {
      this.$store?.commit('setShowAddProject', false);
      this.$store?.commit('setShowProjects', true);
    },

  },
};
</script>

<style>

.zoneAddProject {
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