<template>
  <section class="row justify-content-center about">
    <div class="col-11 col-md-8">
      <h2 v-text="$t('footer.about')" class="mb-4">O projektu</h2>
      <div>
        <p id="jhi-delete-advertisement-heading">
          Portal otvorene istraživačke infrastrukture Srbije je zamišljen kao jedinstveno mesto za nalaženje infrastruktura i njihovih
          usluga potrebnih u naučnoistraživačkom radu i razvoju inovativnih proizvoda.
        </p>
        <p>
          Po zvaničnoj definiciji Evropske unije, istraživačke infrastrukture su svi resursi koji pružaju usluge istraživačkoj zajednici u
          cilju sprovođenja istraživanja i podržavanja inovacija. Mogu se koristiti i van istraživanja, npr. za obrazovanje ili javne
          usluge. Mogu biti jednostrani, distribuirani ili virtuelni. Tu su uključeni i važna naučna oprema ili skupovi instrumenata,
          zbirke, arhive ili naučni podaci, računarski sistemi i komunikacione mreže, bilo koja druga istraživačka i inovativna
          infrastruktura jedinstvene prirode koja je otvorena za eksterne korisnike. Neki od primera istraživačke infrastrukture su:
        </p>
        <ul>
          <li>DNK sekvencioner.</li>
          <li>Kidalica za merenje mehaničkih karakteristika materijala.</li>
          <li>Laboratorijska pivara.</li>
          <li>Ogledno poljoprivredno dobro.</li>
          <li>Meteorološka baza podataka Srbije.</li>
          <li>Repozitorijum medicinskih slika (rentgen, CT, MRI).</li>
          <li>Data centar.</li>
          <li>Tim finansijskih eksperata.</li>
          <li>Program za simulaciju ponašanja struktura.</li>
        </ul>
        <p>
          Otvorena istraživačka infrastruktura je sva infrastruktura kojoj imaju pristup istraživači iz drugih organizacija (i
          laboratorija). Pristup infrastrukturi može biti:
        </p>
        <ul>
          <li>
            <strong>Vođen istraživanjem</strong> - Pristup na osnovu relevantnosti, odobren nakon plaćanja dodatnih troškova u vezi sa
            pristupom u odnosu na normalan rad istraživačke infrastrukture
          </li>
          <li><strong>Vođen tržištem</strong> – Tržišno-orijentisan pristup, odobrava se nakon plaćanja pune naknade za pristup.</li>
          <li><strong>Vođen obrazovanjem</strong> – Privlačenje mladih talenata.</li>
        </ul>
        <p>Svrha portala se ogleda u sledećem:</p>
        <ul>
          <li>
            Portal omogućuje da naučnoistraživačke organizacije (NIO) izlože postojeću istraživačku infrastrukturu i usluge koje pružaju.
          </li>
          <li>
            Da zainteresovane organizacije pronađu usluge zasnovane na istraživačkoj infrastrukturi i saznaju pod kojim uslovima se mogu
            dobiti.
          </li>
        </ul>
        <p>Koristi od upotrebe portala koju mogu imati <strong>privredne organizacije i preduzetnici</strong> su:</p>
        <ul>
          <li>Nalaženje opreme koja je potrebna za razvoj i testiranje inovativnih proizvoda i usluga u zemlji.</li>
          <li>Nalaženje usluga kojim se ubrzava proces razvoja, optimizacije i testiranja proizvoda i usluga u zemlji.</li>
          <li>Bolje planiranje razvoja inovativnih proizvoda i usluga.</li>
          <li>Skraćivanje vremena od ideje za novi proizvod ili uslugu do tržišta.</li>
          <li>Poboljšanje kvaliteta proizvoda i usluga efikasnije korišćenje sopstvenih razvojnih kapaciteta.</li>
        </ul>
        <p>Koristi od upotrebe portala koju mogu imati <strong>naučno istraživačke organizacije</strong> su:</p>
        <ul>
          <li>Proširivanje saradnje sa privrednim organizacijama, društvenim institucijama i drugim NIO iz Srbije i inostranstva.</li>
          <li>Dolaženje do ideja i partnera za projekte.</li>
          <li>Podizanje kapaciteta tehničkog i naučno-istraživačkog osoblja.</li>
          <li>Dodatni prihodi od pružanja usluga.</li>
          <li>Olakšano pokrivanje troškova amortizacije i održavanja opreme.</li>
          <li>Shvatanje potreba za novim uslugama istraživanja i razvoja.</li>
          <li>Povećanje vidljivosti i ugleda NIO.</li>
          <li>Priliv talenata iz zemalja u okruženju i šire.</li>
        </ul>
        <p>Koristi od upotrebe portala koju mogu imati <strong>istraživači iz NIO</strong> su:</p>
        <ul>
          <li>Rad na realnim istraživačko-razvojnim izazovima u privrednim i društvenim organizacijama.</li>
          <li>Tešnja saradnja sa privredom, drugim NIO i društvenim organizacijama.</li>
          <li>Otkrivanje realnih razvojnih izazova i kreiranje naučnih i inovativnih projekata za njihovo rešavanje.</li>
          <li>Podizanje sopstvenih istraživačkih kapaciteta.</li>
          <li>Zadovoljstvo primene stečenog znanja i veština u rešavanju realnih razvojnih problema.</li>
          <li>Dodatni prihodi.</li>
        </ul>
        <p>
          Koristi od upotrebe portala koju mogu imati
          <strong>Ministarstvo nauke, tehnološkog razvoja i inovacija, Fond za nauku i Fond za inovacionu delatnost</strong> su:
        </p>
        <ul>
          <li>Praćenje efikasnosti korišćenja postojeće istraživačke infrastrukture.</li>
          <li>Bolje razumevanje potreba naučnih, privrednih i društvenih organizacija za novom istraživačkom infrastrukturom.</li>
          <li>Donošenje odluka po zahtevima za nabavku nove opreme bazirano na činjenicama.</li>
          <li>Izbegavanje dupliranja kapaciteta.</li>
          <li>Bolji naučno-istraživački rezultati.</li>
          <li>Digitalizovana podrška inovacionom eko sistemu Srbije.</li>
        </ul>
        <p>Koristi od upotrebe portala koju može imati <strong>država i društvo u celini</strong> su:</p>
        <ul>
          <li>Efikasnije korišćenje opreme i povratak investicije pre nego što oprema zastari.</li>
          <li>Brži razvoj malih i srednjih preduzeća i privrednih organizacija generalno.</li>
          <li>Ubrzani privredni rast.</li>
          <li>Podrška implementacije pametne specijalizacije i građanske nauke.</li>
          <li>Zaustavljanje odliva visokostručnog kadra iz zemlje.</li>
          <li>Bolji ugled države u okruženju i svetu.</li>
          <li>Bolje razumevanje građana o opravdanosti ulaganja u nauku i istraživanje.</li>
        </ul>
        <p>
          Suština portala je da se intenziviranjem korišćenja postojeće istraživačke infrastrukture znatno poveća inovacioni kapacitet
          srpske privrede, čime bi se poboljšali i naučni rezultati NIO i to bez dodatnih ulaganja.
        </p>
      </div>
    </div>
  </section>
</template>
