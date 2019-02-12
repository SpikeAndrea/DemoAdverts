<template>
  <div class="update">

      <h2 v-if="isUpdate">UPDATE CAR ADVERT</h2>
      <h2 v-else>NEW CAR ADVERT</h2>
  <div>      
    <table>
        <tr v-if="isUpdate"><td><input type="text" placeholder="id" v-model="id" readonly=true></td></tr>
      <tr><td><input type="text" placeholder="Title of car" v-model="title"></td></tr>
      <tr><td><input type="text" placeholder="Fuel" v-model="fuel"></td></tr>
      <tr><td><input type="text"  placeholder="Price" v-model="price"></td></tr>
      <tr><td><fieldset>
                    <legend>State of car</legend>
                    <input id="chkNew" type="radio" name="rdStateCar" value="0" v-model="state" checked="true">
                    <label for="chkNew">New</label>
                    <input id="chkUsed" type="radio" name="rdStateCar" value="1" v-model="state" >
                    <label for="chkUsed">Used</label>
                </fieldset>
      </td></tr>
      <tr v-if="state > 0"><td><input type="text" placeholder="MileAge" v-model="mileage" ></td></tr>
      <tr v-if="state > 0"><td><input type="date" placeholder="Fisrt Registration" v-model="firstreg" ></td></tr>
    </table>    
    <table>
        <tr v-if="isUpdate">
            <td><input type="button" value="Save" @click="updateAdvert"/></td>
            <td><input type="button" value="Delete" @click="deleteAdvert"/></td>
        </tr>
        <tr v-else>
            <td><input type="button" value="Save" @click="newAdvert"/></td>
        </tr>
    </table>
    
    <p v-if="errors.length">
        <b>Message</b>
        <ul>
            <li v-for="error in errors" :key="error.id">
                {{error}}
            </li>
        </ul>
    </p>
    </div>
  </div>  
</template>

<script>
/*
Essendo che le pagine per aggiornare, cancellare ed inserire un annuncio, utilizzano gli stessi
campi, creiamo questo componente, che a seconda delle circostanze verrà utilizzato in Update.vue
per aggiornare o cancellare i dati, oppure in NewAdvert.vue per l'inserimento di nuovi dati
*/

export default{
    name: 'edit',
    data(){
        return{
        errors: [],  //indica gli errori per la validazione
        isUpdate: false, //indica se ci troviamo nella pagina di update oppure di new
        advert:[],  //è l'array con gli annunci
        id:null,
        title: null,
        fuel: null,
        price: null,
        mileage:null,
        firstreg:null,        
        state: 0
        }
    },
    mounted: function(){                 
        //Se sono in update, devo leggermi i dati relativi a quell'id
        if (this.$route.name == "update"){
           this.isUpdate = true //setto isUpdate a true
           this.loadAdvert();
        }
    },
    methods:{           
        loadAdvert: function(){                  
           //Effettuo la chiamata passando come id il parametro di $route.
           //$route è la tabella di routing, se si apre il file router.js, 
           //si vedrà che il path di Update è cosi definito:
           //path: '/update/:id'   -id è appunto il parametro che ci interessa    
           $.getJSON('http://localhost:9000/cars/' + this.$route.params.id)
           .done(data => {
                this.advert = data;  //carico i dati letti nell'array
                this.id = this.advert.id;              
               this.title = this.advert.title;
               this.fuel = this.advert.fuel;
                this.price = this.advert.price;
                this.state = this.advert.state;
                this.mileage = this.advert.mileage;
                this.firstreg = this.advert.firstreg;
           });                                           
        } ,
        updateAdvert: function(){  //Aggiorno i dati 
            this.errors = []; //iniializzo gli errori
            this.check(); //effettuo un check, il metodo check alimenta this.errors in caso di errori

            //se ci sono errori, esco
            if (this.errors.length){                
                return;
            }
            //Altrimenti se è un veicolo nuovo, resetto i mileage e firstreg
            if (this.state == 0){
                this.mileage = null;
                this.firstreg = null;
            }
            
            //Preparo la chiamat, url e dati json
            let urlUpdate = 'http://localhost:9000/cars/' + this.$route.params.id;
            var jsonData = JSON.stringify({title: this.title, fuel: this.fuel,price: this.price, state: this.state,mileage: this.mileage,firstreg: this.firstreg});
            //Effettuo la chiamata           
            $.ajax({
              type: "POST",
              url: urlUpdate,
              data: jsonData,
              contentType: "application/json; charset=utf-8",
              success: function(result){
                  console.log(result);
              },
              error: function(error){
                  console.log(error);
              }
            });

            this.errors = [];
        },
        newAdvert:function(){ //Inserisco un nuovo annuncio
            //Controllo delle validazioni
            this.errors = [];
            this.check();
            if (this.errors.length){                
                return;
            }
            if (this.state == 0){
                this.mileage = null;
                this.firstreg = null;
            }
            
            let urlNew = 'http://localhost:9000/cars/';
            var jsonData = JSON.stringify({title: this.title, fuel: this.fuel,price: this.price, state: this.state,mileage: this.mileage,firstreg: this.firstreg});           
            $.ajax({
              type: "POST",
              url: urlNew,
              data: jsonData,
              contentType: "application/json; charset=utf-8",
              success: function(result){
                  console.log(result);
              },
              error: function(error){
                  console.log(error);
              }
            });

            this.errors = [];
        },
        deleteAdvert: function(){ //Cancello annuncio
            let vm = this;
            let urlDelete = 'http://localhost:9000/cars/rem/' + this.$route.params.id;
                       
            $.ajax({
              type: "POST",
              url: urlDelete,
              contentType: "application/json; charset=utf-8",
              success: function(){
                  console.log("Car advert removed");
                  vm.errors.push("Car Advert removed");
              },
              error: function(error){
                  console.log(error);
              }
            });
        },
         check: function(){ //Effettuo un check per la validazione dei campi           

            this.errors = [];

            if(!this.title){
                this.errors.push('Title required');
            }

            if (!this.fuel){
                this.errors.push('Fuel required');
            }

            if (!this.price){
                this.errors.push("Price required");
            }else{
                let isNumber = Number(this.price); //controllo che il prezzo inserito sia un numero
                if (isNaN(isNumber)){
                    this.errors.push("Price not valid");
                }
            }

            if (this.state == 1){ //solo se il veicolo è usato
                if (!this.mileage){
                    this.errors.push("Mileage required"); //come per il prezzo
                }else{
                    let isNumber = Number(this.price);
                if (isNaN(isNumber)){
                    this.errors.push("Price not valid");
                }
                }

                if (!this.firstreg){
                    this.errors.push("First reg. required");
                }
            }

        
        }              
    }
}
</script>
