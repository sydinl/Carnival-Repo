import Head from 'next/head';
import ArrowUpOnSquareIcon from '@heroicons/react/24/solid/ArrowUpOnSquareIcon';
import ArrowDownOnSquareIcon from '@heroicons/react/24/solid/ArrowDownOnSquareIcon';
import PlusIcon from '@heroicons/react/24/solid/PlusIcon';
import {
  Box,
  Button,
  Container,
  Pagination,
  Stack,
  SvgIcon,
  Typography,
  Unstable_Grid2 as Grid,
  TextField
} from '@mui/material';
import { Layout as DashboardLayout } from 'src/layouts/dashboard/layout';
import { CompanyCard } from 'src/sections/companies/company-card';
import { CompaniesSearch } from 'src/sections/companies/companies-search';
import { useState } from 'react';
import { triggerTemplate } from 'src/api/report-template';



const Page = () => {

  const handleTrigger = () => {
    triggerTemplate(templateName).then((res) => console.log(res))
  }
  const [templateName, setTemplateName] = useState('')
  return (
    <>
      <Head>
        <title>
          Carnival Trigger
        </title>
      </Head>
      <Box
        component="main"
        sx={{
          flexGrow: 1,
          py: 8
        }}
      >
        <Container maxWidth="xl">
          <Grid
            container
            spacing={5}
          >
            <Grid
              xs={12}
              md={6}
            >
              <TextField
                fullWidth
                label={'Please input template Name'}
                name={'teamplateName'}
                onChange={(e) => setTemplateName(e.target.value)}
                required
                value={templateName}
              />
            </Grid>
            <Grid
              xs={12}
              md={6}
            >
              <Button variant="contained" onClick={handleTrigger} >
                Trigger
              </Button>
            </Grid>
          </Grid>
        </Container>
      </Box >
    </>)
}

Page.getLayout = (page) => (
  <DashboardLayout>
    {page}
  </DashboardLayout>
);

export default Page;
