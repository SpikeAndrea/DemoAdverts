package com.example.demo;

import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
class CarController {

    private final CarRepository repository;

    //Constructor
    public CarController(CarRepository repository){
        this.repository = repository;
    }

    //Ritorna gli annunci (default ordinamento = id)
    @GetMapping("/cars")
    public Iterable<Car> getAllCars(){
        return repository.findAll();
    }

    //Ritorna gli annunci ordinati in base alla colonna specificata da {columnOrder} e con la
    //direzione specificata dal parametro ?dir=
    @RequestMapping(value = "/carsOrderBy/{columnOrder}", method = RequestMethod.GET)
    public Iterable<Car> sortCars(@PathVariable String columnOrder,@RequestParam("dir") String dir){
        if (dir.equals("ASC")){
            return  repository.findAll(Sort.by(columnOrder).ascending());
        }else {
            return  repository.findAll(Sort.by(columnOrder).descending());
        }
    }

    //Ritorna gli annunci che hanno il titolo che che corrisponde titletofind
    @RequestMapping(value = "/carsByTitle/{titleToFind}", method = RequestMethod.GET)
    public Iterable<Car> searchCars(@PathVariable String titleToFind){
        //Non faccio distinzione tra minuscole e maiuscole, e non
        //ho bisogno di una corrispondenza completa nella ricreca
        return repository.findByTitleIgnoreCaseContaining(titleToFind);

    }

    //Ritorna un determinato annunncio
    @RequestMapping(value = "/cars/{id}", method = RequestMethod.GET, produces = "application/json" )
    Car getById(@PathVariable Integer id){
        //L'annunncio è identificato tramite il suo id
        return repository.findById(id).orElseThrow(()-> new CarException(id));
    }

    //Inserisce un nuovo annuncio
    @RequestMapping(value = "/cars", method = RequestMethod.POST)
    Car AddNewCar(@RequestBody Car carToInsert){
        return  repository.save(carToInsert);
    }

    //Aggiorna un annuncio già esistente
    @RequestMapping(value = "/cars/{id}", method = RequestMethod.POST, produces = "application/json", consumes = "application/json" )
    Car UpdateCar(@PathVariable Integer id, @RequestBody Car carWithUpdate){
        return  repository.findById(id)
                .map(car -> {
                    car.setTitle(carWithUpdate.getTitle());
                    car.setFuel(carWithUpdate.getFuel());
                    car.setPrice(carWithUpdate.getPrice());
                    car.setState(carWithUpdate.getState());
                    car.setMileage(carWithUpdate.getMileage());
                    car.setFirstreg(carWithUpdate.getFirstreg());
                    return repository.save(car);
                }).orElseThrow(()-> new CarException(id));

    }

    //Cancella un annuncio
    @RequestMapping(value = "/cars/rem/{id}", method = RequestMethod.POST)
    void DeleteCar(@PathVariable Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        }

    }
}
