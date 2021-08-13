<template>
  <div>
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
        <MemberContainer @openProfileModal="handleOpenProfileModal" />
      </section>

      <!-- 임시 멤버 (onBoard 등록된 멤버)  -->
      <section>
        <div class="header">
          <h1 class="section-title">On Board</h1>
        </div>
        <div class="onboard-container">
          <ul>
            <OnBoardMemberListItem
              v-for="onBoardMember in onBoardList"
              :key="onBoardMember.onboardId"
              :member="onBoardMember"
            />
          </ul>
        </div>
      </section>
    </div>
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
import MemberContainer from "@/components/Members/MemberContainer.vue"
import AddMemberModal from "@/components/Members/AddMemberModal.vue"
import ProfileModal from "@/components/Members/ProfileModal.vue"
import OnBoardMemberListItem from "@/components/Members/OnBoardMemberListItem.vue"

export default {
  name: "Members",
  components: {
    MemberContainer,
    AddMemberModal,
    ProfileModal,
    OnBoardMemberListItem,
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

    const handleOpenProfileModal = userId => {
      profileModalOpen.value = true
      profileUserId.value = userId
    }

    // =====================================================
    // On Board 관련
    const onBoardList = computed(() => store.state.onboard.onBoardList)
    onMounted(async () => {
      try {
        await store.dispatch(
          "onboard/getOnBoardList",
          store.state.auth.user.officeEntity.officeId
        )
      } catch (error) {
        console.log(error.message)
      }
    })
    // On Board 관련 끝
    // =====================================================

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
      onBoardList,
      handleOpenProfileModal,
    }
  },
}
</script>

<style scoped lang="scss">
.members {
  @apply grid gap-10 px-10 my-10;

  section {
    @apply grid gap-6;

    .header {
      @apply flex gap-2 items-center justify-between select-none;

      .section-title {
        @apply text-2xl font-bold;
      }
      .add-member-btn {
        @apply py-2 px-4 flex items-center font-medium rounded bg-gray-200 transition;

        &:hover {
          @apply bg-blue-700 text-white;
        }
      }
    }

    .onboard-container {
      max-height: 250px;
      @apply flex flex-col bg-white rounded-lg shadow overflow-auto;
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
