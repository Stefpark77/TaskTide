<template>
    <table class="table-auto w-full content-center" summary="Events table">
        <thead>
            <tr>
                <th class="px-4 py-2">Name</th>
                <th class="px-4 py-2">Description</th>
                <th class="px-4 py-2">Date</th>
                <th class="px-4 py-2">Edit</th>
                <th class="px-4 py-2">Delete</th>
            </tr>
        </thead>
        <tbody class="text-center">
            <tr v-for="event in this.$store?.state?.events" :key="event.id">
                <td class="border px-4 py-2">{{ event.name }}</td>
                <td class="border px-4 py-2">{{ event.description }}</td>
                <td class="border px-4 py-2">{{ event.date }}</td>
                <td class="border px-4 py-2">
                    <button
                        class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
                        type="button" @click="updateEvent(event.id)"
                    >
                        Edit
                    </button>
                </td>
                <td class="border px-4 py-2">
                    <button
                        class="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
                        type="button" @click="removeEvent(event.id)"
                    >
                        Delete
                    </button>
                </td>
            </tr>
        </tbody>
    </table>
    <div class="mt-5"/>

    <div v-if="!this.$store?.state?.showAddEvent">
        <div class="flex justify-end">
        <button
            class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
            type="button"
            v-on:click="addEvent"
        >
            Add Event
        </button>
    </div>
    </div>
    
</template>

<script>
export default {
    data() {
        return {
            events: this.$store?.state?.events ?? [],
        };
    },
    methods: {
        removeEvent(id) {
            this.$store?.commit('removeEvent', id);
        },
        addEvent() {
            this.$store?.commit('setShowAddEvent', true);
        },
        updateEvent(id) {
            this.$store?.commit('setUpdateEvent', id);
        },
    },
};
</script>