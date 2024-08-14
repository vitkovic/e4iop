<template>
  <section class="row justify-content-center contact">
    <div class="col-11 col-md-8">
        <h2 v-text="$t('footer.contact')" class="mb-4">Kontakt</h2>
        <div class="col-12">
          <b-form>
            <b-form-group id="input-group-1" label="Oblast*" label-for="input-1">
              <b-form-select v-model="selected" id="input-1">
                <b-form-select-option>Izaberite</b-form-select-option>
              </b-form-select>
            </b-form-group>
            <b-form-group id="input-group-2" label="Koju vrstu odgovora želite*" label-for="input-2">
              <b-form-select v-model="selected" id="input-2">
                <b-form-select-option>Izaberite</b-form-select-option>
              </b-form-select>
            </b-form-group>

            <b-form-group id="input-group-3" label="Ime*" label-for="input-3">
              <b-form-input id="input-3" placeholder="Ime*" required></b-form-input>
            </b-form-group>

            <b-form-group id="input-group-4" label="Prezime*" label-for="input-4">
              <b-form-input id="input-4" placeholder="Prezime*" required></b-form-input>
            </b-form-group>

            <b-form-group id="input-group-5" label="Adresa" label-for="input-5">
              <b-form-input id="input-5" placeholder="Adresa" required></b-form-input>
            </b-form-group>

            <b-form-group id="input-group-6" label="Kontakt telefon*" label-for="input-6">
              <b-form-input id="input-6" placeholder="Kontakt telefon*" required></b-form-input>
            </b-form-group>

            <b-form-group id="input-group-7" label="Kontakt email*" label-for="input-7">
              <b-form-input id="input-7" placeholder="Kontakt email*" required></b-form-input>
            </b-form-group>

            <b-form-group id="input-group-8" label="Upit*" label-for="input-8">
              <b-form-textarea id="textarea" v-model="text" rows="3" max-rows="6"></b-form-textarea>
            </b-form-group>

            <b-button type="submit" variant="primary">Pošalji</b-button>
            <b-button type="reset" variant="danger">Resetuj</b-button>
          </b-form>
        </div>
    </div>
  </section>
</template>