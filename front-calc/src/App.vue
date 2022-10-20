
<template>
  <div>
    <div class="card">
      <TabMenu :key="reLoad"  :model="items" />
      <router-view/>
    </div>

  </div>

</template>

<script>
import { ref } from 'vue';
import axios from "axios";


export default {
  setup() {
    const active = ref(3);
    const items = ref([
      {
        label: 'Home',
        icon: 'pi pi-fw pi-home',
        to: '/'
      },
      {
        label: 'Calendar',
        icon: 'pi pi-fw pi-calendar',
        to: '/calendar'
      },
      {
        label: 'Edit',
        icon: 'pi pi-fw pi-pencil',
        to: '/edit'
      },

    ])

    return { active, items}},

  data()
  {
    return {
      reLoad:0
    }
  }


}

</script>

<style scoped lang="scss">
::v-deep(.tabmenudemo-content) {
  padding: 2rem 1rem;
}
</style>
