import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RegisterService } from 'src/app/shared/service/register.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-activate',
  templateUrl: './activate.component.html',
  styleUrls: ['./activate.component.scss']
})
export class ActivateComponent implements OnInit {

  constructor(private route: ActivatedRoute,
    private registerService: RegisterService,
    private toastr: ToastrService,
    private router: Router) { }

  ngOnInit(): void {
    const key: string = this.route.snapshot.queryParamMap.get('key');
    this.registerService.activate(key)
    .subscribe(res => {
      this.toastr.info('Successfully activated. Please login.')
      this.router.navigateByUrl('/login');
    })
  }

}
