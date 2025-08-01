<template>
  <div>
    <div v-if="isLoading">Detaylar Yükleniyor...</div>
    <div v-else-if="book">
      <BookCard :book="book" :userId="userId" @action-completed="fetchBookDetails" />
    </div>
    <div v-else>Kitap bulunamadı.</div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import api from '@/services/api';
import BookCard from '@/components/BookCard.vue';

const route = useRoute();
const book = ref(null);
const isLoading = ref(true);
const userId=route.params.userId;

const bookId = route.params.id;

const fetchBookDetails = async () => {
    isLoading.value = true;
    try {
        const response = await api.getBookById(bookId);
        book.value = response.data;
    } catch (error) {
        console.error("Kitap detayı alınamadı:", error);
    } finally {
        isLoading.value = false;
    }
};

onMounted(fetchBookDetails);
</script>
