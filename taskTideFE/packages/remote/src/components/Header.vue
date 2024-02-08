<template>
    <header class="bg-gray-800 p-4 flex justify-between items-center">
        <h1 class="text-white text-2xl">TaskTide</h1>
        <nav>
            <ul class="flex space-x-4">
                <li>
                    <button
                        class="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
                        type="button" @click="logout"
                    >
                        Sign Out
                    </button>
                </li>
            </ul>
        </nav>
    </header>
</template>

<script>
export default {
    methods: {
        logout() {
            this.$store?.commit('logout');
        },
    },
};
</script>