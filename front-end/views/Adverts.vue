<template>
    <div class="adverts">
        <table>
            <tr>
                <td><h2>CARS ADVICE</h2></td>
                <td><input type="text" placeholder="Search Car" v-model="searchCar"></td>
                <td><input type="button" value="Search" @click="Search"/></td>
            </tr>
        </table>
         
    <div>
      <table>
          <tr>
              <td><b>Filter Active: </b></td>
              <td><input type="checkbox" v-model="isFilter"></td>
              <td>Fuel <input type="text" placeholder="Diesel,Gasoile...." v-model="fuel"> </td>
              <td>
                  Price from <input type="text" v-model="priceFrom"> to <input type="text" v-model="priceTo">                  
              </td>
              <td>                
                      <input type="checkbox" value="0" v-model="stateNew" id="chkNew">
                      <label for="chkNew">New</label>
                      <input type="checkbox" value="1" v-model="stateUsed" id="chkUsed">
                      <label for="chkUsed">Used</label>
                  
              </td>
              
          </tr>                      
      </table>
    </div>
    
    <div id="ctnResult">
        <table id="tblResult">
            <thead>
            <tr>
                <th v-for="(column,i) in columns" :key="i"  @click="SortByColumn(column)">{{column}} </th>                
            </tr>
        </thead>
        <tbody>
            <tr v-for="advert in filterAdverts" :key="advert.id" @click="GoToAdvert(advert.id)" >
                <td>{{advert.id}}</td>
                <td>{{advert.title}}</td>
                <td>{{advert.fuel}}</td>
                <td>{{advert.price}}</td>
                <td>{{advert.state}}</td>
                <td>{{advert.mileage}}</td>
                <td>{{advert.firstreg}}</td>
            </tr>
        </tbody>

        </table>
    </div>

    </div>
</template>

<script>
/*
  
*/

export default {
    name: 'adverts',
    data(){
        return{
            adverts:[], //dati json provenineti da chiamat web 
            columns: ['Id','Title','Fuel','Price','State','Mileage','FirstReg'], //colonne della tabella di visualizzazione
            direction: 'ASC', //direzione dell'ordinamento
            lastColOrder: null, //il nome dell'ultima colonna ordinata
            searchCar:null, 
            priceFrom:null,
            priceTo:null,
            stateNew:null,
            stateUsed:null,
            fuel:null,
            isFilter:false
        }
    },
    mounted:function(){        
           //Carico i dati al primo avvio                 
           this.loadAdvert();    
    },
    computed:{
        filterAdverts(){ //filtra gli elementi della nostra tabella di visualizzazione
            var vm = this;
            if (!this.isFilter){ //controlla se il check FilterActive sia attivo
                return this.adverts; //se non lo, restituisce tutti i dati della chiamata
            }else{
            this.filter = false;
            return this.adverts.filter(function(advert) {  //per ogni riga della tabella
                let isOk = true;   //parto dal presupposto che corrisponda al filtro indicato
                //alla prima NON corrispondenza, gli assegno False
                //e naturalmente viene escluso dal filtro indicato
                 

                if ((vm.fuel) && (advert.fuel != vm.fuel )){
                    isOk = false;
                }                

                if ((vm.priceFrom) && (advert.price < vm.priceFrom)){
                    isOk = false;
                }

                if ((vm.priceTo) && (advert.price > vm.priceTo)){
                    isOk = false;
                }
                
                if ((!vm.stateNew) || (!vm.stateUsed)){
                if ((vm.stateNew) && (advert.state != 0)){
                    isOk = false;
                }

                if ((vm.stateUsed) && (advert.state != 1)){
                    isOk = false;
                }
                }

               
                return isOk;               
            });
            
            }
        }
    },
    methods:{        
        loadAdvert: function(){                      
           $.getJSON('http://localhost:9000/cars')
           .done(data => { this.adverts = data; });                                           
        },
        SortByColumn(colSelect){ //ordina cliccando su una colonna
            //prendo il nome della colonna
            colSelect = colSelect.trim(); //elimino gli spazione
            colSelect = colSelect.toLowerCase(); //lo rendo tutto in minuscolo
              
            //Se corrisponde all'ultima colonna ordinata, allora devo cambiare la direzione di
            //ordinamento
            if (colSelect == this.lastColOrder){
                this.direction = (this.direction == "ASC") ? "DESC" : "ASC";                
            }else{ //altrimenti la direzione di ordinamento di default è ASC
                this.direction = "ASC";
            }

            this.lastColOrder = colSelect;

            //Effettuo la chiamata web che mi restituisce i dati voluti ordinati
            //  /carsOrderBy/nomecolonna?dir=direction
            $.getJSON('http://localhost:9000/carsOrderBy/' + colSelect + '?dir=' + this.direction)
            .done(data => { this.adverts = data; }); 
        },
        GoToAdvert(id){            
            this.$router.push({name: 'update', params:{id}});
        },
        Search:function(){ //cerco con una chiamata web attraverso il campo title
           let find = this.searchCar;
           $.getJSON('http://localhost:9000/carsByTitle/' + find )
           .done(data => { this.adverts = data; }); 
        }    
        
    }
}
</script>

