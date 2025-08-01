<template>
  <div>
    <h1>Yeni Kitap Ekle</h1>
    <AddBookForm @book-added="onBookAdded" />
  </div>
</template>

<script setup>
import AddBookForm from '@/components/AddBookForm.vue';
import { useRouter } from 'vue-router';

const router = useRouter();

const onBookAdded = () => {
  alert('Kitap başarıyla eklendi, ana sayfaya yönlendiriliyorsunuz.');
  router.push({ name: 'books' });
};
</script>
