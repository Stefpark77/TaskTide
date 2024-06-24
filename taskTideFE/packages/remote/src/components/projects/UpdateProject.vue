<template>
  <div v-if="this.$store?.state?.showEditProject">
    <div class="zoneAddProject">
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

            <v-text-field type="date"
                          label="Deadline"
                          :rules="[value => value !== '' || 'This field is required.',
                        value => parseISO(value) > Date.now() || 'The deadline must be after today\'s date!']"
                          v-model="updateDeadline"
            ></v-text-field>

            <div class="flex items-end justify-end">
              <v-btn
                  class="mr-5"
                  type="button" @click="cancelUpdate">
                Cancel
              </v-btn>
              <v-btn
                  class="bg-red-200 hover:bg-red-700 text-white font-bold py-2 px-4 rounded-2xl focus:outline-none focus:shadow-outline mr-5"
                  type="button" @click="removeProject(editProjectId)">
                Delete
              </v-btn>
              <v-btn
                  :disabled="updateName === '' ||
             updateDescription === '' ||
             updateDeadline === '' || parseISO(updateDeadline) <= Date.now()"
                  class="left-0 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-2xl focus:outline-none focus:shadow-outline disabledFunction"
                  type="button" @click="updateProject">
                Update Project
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
      editProjectId: this.$store?.state?.editProjectId ?? '',
      updateName: this.$store?.state?.updateName ?? '',
      updateDescription: this.$store?.state?.updateDescription ?? '',
      updateDeadline: this.$store?.state?.updateDeadline ?? '',
      projectTasks: this.$store?.state?.projectTasks ?? [],

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
    updateDeadline: {
      handler: function (val) {
        this.$store?.commit('setUpdateDate', val);
      },
      deep: true,
    },
  },
  methods: {
    parseISO,
    updateProject() {
      this.$store?.commit('updateProject', {
        name: this.updateName,
        description: this.updateDescription,
        deadline: this.updateDeadline
      });
      this.$store?.commit('setShowEditProject', false);
      this.$store?.commit('setShowProjects', true);
      const dateObject = parseISO(this.updateDeadline);
      const newDeadline = format(dateObject, "yyyy-MM-dd'T'HH:mm:ss.SSSxxx");
      for (let task of this.projectTasks) {
        this.$store?.commit('updateTaskForProject', {
          task: task,
          project: {id: this.editProjectId, deadline: newDeadline},
          projectId: this.editProjectId
        });
      }
    },
    cancelUpdate() {
      this.$store?.commit('setShowEditProject', false);
      this.$store?.commit('setShowProjectPage', true);
    },
    removeProject(id) {
      this.$store?.commit('removeProject', id);
      this.$store?.commit('setShowEditProject', false);
      this.$store?.commit('setShowProjects', true);
      for (let task of this.projectTasks) {
        this.$store?.commit('updateTaskForProject', {
          task: task,
          project: {id: null, deadline: null},
          projectId: this.editProjectId
        });
      }
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