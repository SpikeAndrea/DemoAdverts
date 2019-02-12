<template>
<div class="newadvert">
    <EditAdver></EditAdver>    
</div>
</template>

<script>
//Importo il componente per editare i dati, il componente a seconda del path del route
//riconosce se stiamo modificano i dati, oppure se ne stiamo inserendo di nuovi
import EditAdver from '@/components/EditAdver.vue'

export default {
    name: 'newadvert',
    components:{
        EditAdver
    }
}
</script>