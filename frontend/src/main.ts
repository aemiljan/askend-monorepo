import { bootstrapApplication } from '@angular/platform-browser';
import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { provideAnimations } from '@angular/platform-browser/animations';
import {MatDialogModule} from '@angular/material/dialog';
import { AppComponent } from './app/app.component';
import { appConfig } from './app/app.config';
import {importProvidersFrom} from '@angular/core';

bootstrapApplication(AppComponent, {
  ...appConfig,
  providers: [
    ...(appConfig.providers || []),
    provideHttpClient(withInterceptorsFromDi()),
    provideAnimations(),
    importProvidersFrom(MatDialogModule)
  ]
}).catch(err => console.error(err));
