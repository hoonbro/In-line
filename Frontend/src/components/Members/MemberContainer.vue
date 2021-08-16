<template>
  <div class="members-container">
    <div class="department-container">
      <div class="header">
        <span>{{ officeName }}</span>
        <span>{{ totalUserCount }}명</span>
      </div>
      <div class="departments-container">
        <ul class="department-list">
          <DepartmentListItem
            v-for="dept in deptList"
            :key="dept.deptName"
            :selected="selectedDeptFilter === dept.deptName"
            :name="dept.deptName"
            :count="dept.cnt"
            @click="selectFilter(dept.deptName)"
          />
        </ul>
      </div>
    </div>
    <div class="user-container">
      <div class="search-container">
        <button class="search-btn">
          <span class="material-icons">search</span>
        </button>
        <input
          v-model.trim="searchTerm"
          type="text"
          placeholder="이름을 검색할 수 있어요."
        />
      </div>
      <div class="users-container">
        <ul class="user-list" v-if="searchedMembers && searchedMembers.length">
          <MemberListItem
            v-for="member in searchedMembers"
            :key="member.userId"
            :member="member"
            @click="$emit('openProfileModal', member.userId)"
          />
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from "@vue/reactivity"
import { useStore } from "vuex"
import { computed, onMounted } from "@vue/runtime-core"
import MemberListItem from "@/components/Members/MemberListItem.vue"
import DepartmentListItem from "@/components/Members/DepartmentListItem.vue"

export default {
  name: "MembersContainer",
  emits: ["openProfileModal"],
  components: { MemberListItem, DepartmentListItem },
  setup() {
    const store = useStore()
    const searchTerm = ref("")
    const totalUserCount = computed(
      () => store.state.admin.members.officeUserCount[0]
    )
    const deptList = computed(() => store.state.admin.members.deptUserCount)
    const officeName = computed(
      () => store.state.auth.user.officeEntity.officeName
    )
    const selectedDeptFilter = ref("")
    const searchedMembers = computed(() => {
      return store.state.office.members
        .filter(member => {
          return member.name.includes(searchTerm.value)
        })
        .filter(member => {
          return selectedDeptFilter.value
            ? member.deptEntity.deptName === selectedDeptFilter.value
            : true
        })
    })

    const selectFilter = deptName => {
      if (selectedDeptFilter.value === deptName) {
        selectedDeptFilter.value = ""
      } else {
        selectedDeptFilter.value = deptName
      }
    }

    onMounted(async () => {
      try {
        const members = await store.dispatch("admin/getOrganization")
        console.log(members)
      } catch (error) {
        store.commit("landing/addAlertModalList", {
          type: "error",
          message: error.message,
        })
      }
    })

    return {
      totalUserCount,
      deptList,
      searchTerm,
      officeName,
      searchedMembers,
      selectedDeptFilter,
      selectFilter,
    }
  },
}
</script>

<style lang="scss" scoped>
@media (min-width: 1024px) {
  .members-container {
    height: 504px;
  }
}

.members-container {
  @apply grid gap-4 grid-cols-12;

  .department-container {
    @apply col-span-12 lg:col-span-4 h-60 lg:h-full grid content-start bg-white rounded-lg shadow overflow-hidden;

    .header {
      @apply p-4 border-b border-gray-300 font-medium flex justify-between bg-gray-50 select-none;
    }

    .departments-container {
      @apply overflow-auto;

      .department-list {
        @apply grid;
      }
    }
  }

  .user-container {
    @apply flex flex-col col-span-12 lg:col-span-8 h-96 lg:h-full bg-white rounded-lg shadow overflow-hidden;

    .search-container {
      @apply flex-shrink-0 p-4 border-b flex items-center relative;

      .search-btn {
        @apply p-2 w-10 h-10 bg-gray-100 rounded mr-4;

        span {
          @apply text-gray-400;
        }
      }

      input {
        @apply py-2 px-4 rounded bg-gray-100 w-full;
      }
    }

    .users-container {
      @apply flex-1 overflow-auto h-full;

      .user-list {
        @apply grid;
      }
    }
  }
}
</style>
