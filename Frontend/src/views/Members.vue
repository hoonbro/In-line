<template>
  <div class="members">
    <section>
      <div class="header">
        <h1 class="section-title">구성원</h1>
        <button
          class="add-member-btn"
          v-if="true"
          @click="addMemberModalOpen = true"
        >
          <span class="material-icons">add</span>
          <span>구성원 추가</span>
        </button>
      </div>
      <div class="members-container">
        <div class="department-container">
          <div class="header">
            {{ officeName }}
          </div>
          <div class="departments-container">
            <ul class="department-list">
              <DepartmentListItem :name="'FE'" :count="3" />
              <DepartmentListItem :name="'BE'" :count="3" />
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
            <ul
              class="user-list"
              v-if="searchedMembers && searchedMembers.length"
            >
              <MemberListItem
                v-for="member in searchedMembers"
                :key="member.userId"
                :member="member"
                @click="openProfileModal(member.userId)"
              />
            </ul>
          </div>
        </div>
      </div>
    </section>
    <AddMemberModal
      v-if="addMemberModalOpen"
      @close="addMemberModalOpen = false"
    />
    <ProfileModal
      v-if="profileModalOpen"
      @close="profileModalOpen = false"
      :userId="profileUserId"
    />
  </div>
</template>

<script>
import { computed, onMounted, ref } from "vue"
import { useStore } from "vuex"
import MemberListItem from "@/components/Members/MemberListItem.vue"
import DepartmentListItem from "@/components/Members/DepartmentListItem.vue"
import AddMemberModal from "@/components/Members/AddMemberModal.vue"
import ProfileModal from "@/components/Members/ProfileModal.vue"

export default {
  name: "Members",
  components: {
    MemberListItem,
    DepartmentListItem,
    AddMemberModal,
    ProfileModal,
  },
  setup() {
    const store = useStore()
    const searchTerm = ref("")
    const addMemberModalOpen = ref(false)
    const profileModalOpen = ref(false)
    const profileUserId = ref(null)
    const officeName = computed(
      () => store.state.auth.user.officeEntity.officeName
    )
    const searchedMembers = computed(() => {
      return store.state.office.members.filter(member => {
        if (searchTerm.value) {
          return member.name.includes(searchTerm.value)
        } else {
          return true
        }
      })
    })

    const openProfileModal = userId => {
      profileUserId.value = userId
      profileModalOpen.value = true
    }

    return {
      officeName,
      searchTerm,
      searchedMembers,
      addMemberModalOpen,
      profileModalOpen,
      openProfileModal,
      profileUserId,
    }
  },
}
</script>

<style scoped lang="scss">
.members {
  scrollbar-width: none;
  @apply bg-gray-100 p-10 grid gap-10 content-start overflow-auto;

  &::-webkit-scrollbar {
    display: none;
  }

  section {
    @apply grid gap-6;

    .header {
      @apply flex items-center justify-between select-none;

      .section-title {
        @apply text-2xl font-bold;
      }
      .add-member-btn {
        @apply py-2 px-4 flex items-center bg-blue-800 text-white font-medium rounded;
        .material-icons {
          @apply mr-2;
        }
      }
    }

    .members-container {
      height: 504px;
      @apply grid gap-4 grid-cols-12;

      .department-container {
        @apply col-span-4 h-full grid content-start bg-white rounded-lg shadow overflow-hidden;

        .header {
          @apply p-4 text-sm border-b;
        }

        .departments-container {
          @apply overflow-auto;

          .department-list {
            @apply grid;
          }
        }
      }

      .user-container {
        @apply flex flex-col col-span-8 bg-white rounded-lg shadow overflow-hidden;

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
  }
}
</style>
