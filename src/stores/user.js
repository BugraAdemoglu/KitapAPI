import { defineStore } from 'pinia';
import api from '@/services/api';


export const useUserStore = defineStore('user', {

  state: () => ({
    usersList: [],
    currentUser: null,
  }),


  actions: {

    async fetchUsers() {
      try {
        const response = await api.getUsers();
        this.usersList = response.data;
      } catch (error) {
        console.error("Kullanıcılar alınamadı:", error);

        this.usersList = [];
      }
    },
  },
});
